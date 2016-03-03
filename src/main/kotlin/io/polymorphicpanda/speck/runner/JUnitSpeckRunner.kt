package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.Speck
import io.polymorphicpanda.speck.core.Clause
import io.polymorphicpanda.speck.core.RootClause
import io.polymorphicpanda.speck.core.ThenClause
import io.polymorphicpanda.speck.core.WhenClause
import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunNotifier
import java.io.Serializable
import java.util.*

data class JUnitUniqueId(val id: Int) : Serializable {
    companion object {
        var id = 0
        fun next() = JUnitUniqueId(id++)
    }
}

internal class JUnitSpeckRunner<T: Speck>(val speck: Class<T>): Runner() {

    val _description by lazy(LazyThreadSafetyMode.NONE) {
        val instance = speck.newInstance()
        val desc = Description.createSuiteDescription(speck)

        RootClause(instance.init, desc.displayName).execute { givenClause ->
            val givenDesc = describe(givenClause)
            desc.addChild(givenDesc)
            rememberAction(givenDesc) {
                givenClause.execute { clause ->
                    val clauseDesc = describe(clause)
                    givenDesc.addChild(clauseDesc)
                    rememberAction(clauseDesc) {
                        when (clause) {
                            is WhenClause -> {
                                rememberAction(clauseDesc) {
                                    clause.execute {
                                        val thenDesc = describe(it)
                                        clauseDesc.addChild(thenDesc)
                                        rememberAction(thenDesc, true) {
                                            it.execute()
                                        }
                                    }
                                }
                            }
                            is ThenClause -> {
                                rememberAction(clauseDesc, true) {
                                    clause.execute()
                                }
                            }
                        }
                    }
                }
            }
        }

        desc
    }

    val stack = LinkedHashMap<Description, (RunNotifier) -> Unit>()

    override fun run(notifier: RunNotifier?) {
        stack.forEach { description: Description, action: (RunNotifier) -> Unit ->
            action(notifier!!)
        }
    }

    override fun getDescription(): Description? = _description

    private fun rememberAction(description: Description, terminal: Boolean = false, action: () -> Unit) {
        when (terminal) {
            false -> {
                try {
                    action()
                } catch(e: Throwable) {
                    stack.put(description) {
                        it.fireTestFailure(Failure(description, e))
                    }
                }
            }
            else -> {
                stack.put(description) {
                    try {
                        it.fireTestStarted(description)
                        action()
                    } catch(e: Throwable) {
                        it.fireTestFailure(Failure(description, e))
                    } finally {
                        it.fireTestFinished(description)
                    }
                }
            }
        }

    }
    private fun describe(clause: Clause): Description {
        return when(clause) {
            is ThenClause -> {
                Description.createTestDescription(
                    "${clause.parent?.parent?.description()}.${clause.parent?.description()}",
                    clause.description()
                )
            }
            else -> Description.createSuiteDescription(clause.description(), JUnitUniqueId.next())
        }
    }
}
