package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.Speck
import io.polymorphicpanda.speck.core.*
import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunNotifier
import org.junit.runners.ParentRunner
import java.io.Serializable

data class JUnitUniqueId(val id: Int) : Serializable {
    companion object {
        var id = 0
        fun next() = JUnitUniqueId(id++)
    }
}

fun junitAction(description: Description, notifier: RunNotifier, action: (RunNotifier) -> Unit) {
    if (description.isTest) {
        notifier.fireTestStarted(description)
    }

    try {
        action(notifier)
    } catch(e: Throwable) {
        notifier.fireTestFailure(Failure(description, e))
    } finally {
        if (description.isTest) notifier.fireTestFinished(description)
    }
}

internal class JUnitWhenRunner<T: Speck>(testClass: Class<T>,
                                         val given: Clause<Given>,
                                         val `when`: Clause<When>): ParentRunner<Clause<Then>>(testClass) {

    val _children by lazy(LazyThreadSafetyMode.NONE) {
        val thenCollector = ThenCollector()
        `when`.execute(thenCollector)
        val results: MutableList<Clause<Then>> = mutableListOf()
        thenCollector.iterate { results.add(it) }
        results
    }

    val _description by lazy(LazyThreadSafetyMode.NONE) {
        val desc = Description.createSuiteDescription(`when`.description(), JUnitUniqueId.next())
        children.forEach {
            desc.addChild(describeChild(it))
        }
        desc
    }

    val childrenDescriptions = hashMapOf<String, Description>()

    override fun getDescription(): Description = _description
    override fun describeChild(child: Clause<Then>): Description {
        return childrenDescriptions.getOrPut(child.description(), {
            Description.createSuiteDescription("${child.description()} (${testClass.javaClass.simpleName}.${given.description()}.${`when`.description()})", JUnitUniqueId.next())
        })
    }
    override fun getChildren(): MutableList<Clause<Then>> = _children

    override fun runChild(child: Clause<Then>, notifier: RunNotifier) {
        junitAction(describeChild(child), notifier) {
            `when`.before()
            child.execute(Assertions(Feature(given, `when`, child)))
            `when`.after()
        }
    }
}

internal class JUnitGivenRunner<T: Speck>(testClass: Class<T>,
                                          val given: Clause<Given>): ParentRunner<JUnitWhenRunner<T>>(testClass) {

    val _children by lazy(LazyThreadSafetyMode.NONE) {
        val whenCollector = WhenCollector()
        given.execute(whenCollector)
        val results: MutableList<JUnitWhenRunner<T>> = mutableListOf()
        whenCollector.iterate { results.add(JUnitWhenRunner(testClass, given, it)) }
        results
    }

    val _description by lazy(LazyThreadSafetyMode.NONE) {
        val desc = Description.createSuiteDescription(given.description(), JUnitUniqueId.next())
        children.forEach {
            desc.addChild(describeChild(it))
        }
        desc
    }

    override fun getDescription(): Description = _description
    override fun describeChild(child: JUnitWhenRunner<T>): Description = child.description
    override fun runChild(child: JUnitWhenRunner<T>, notifier: RunNotifier) {
        junitAction(describeChild(child), notifier) {
            child.run(it)
        }
    }

    override fun getChildren(): List<JUnitWhenRunner<T>> = _children
}


internal class JUnitSpeckRunner<T: Speck>(testClass: Class<T>): ParentRunner<JUnitGivenRunner<T>>(testClass) {
    val _children by lazy(LazyThreadSafetyMode.NONE) {
        val speck = newInstance()
        val givenCollector = GivenCollector()
        speck(givenCollector)
        val results: MutableList<JUnitGivenRunner<T>> = mutableListOf()
        givenCollector.iterate {
            results.add(JUnitGivenRunner(testClass, it))
        }
        results
    }

    override fun getChildren(): List<JUnitGivenRunner<T>> = _children

    override fun describeChild(child: JUnitGivenRunner<T>): Description {
        return child.description
    }

    override fun runChild(child: JUnitGivenRunner<T>, notifier: RunNotifier) {
        junitAction(describeChild(child), notifier) {
            child.run(it)
        }
    }

    override fun getName(): String = testClass.javaClass.simpleName

    @Suppress("UNCHECKED_CAST")
    fun newInstance():T = testClass.onlyConstructor.newInstance() as T
}
