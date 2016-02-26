package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.Speck
import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runners.ParentRunner

/**
 * @author Ranie Jade Ramiso
 */
internal class JUnitSpeckRunner<T: Speck>(testClass: Class<T>): ParentRunner<Feature>(testClass) {
    override fun getChildren(): MutableList<Feature> {
        val speck = newInstance()
        speck.walker = FeatureCollector()
        speck.init(speck)
        return speck.features
    }

    override fun describeChild(child: Feature): Description {
        val given = child.root
        given.children.forEach { `when` ->
            given.description.addChild(`when`.description)
            `when`.children.forEach { then ->
                `when`.description.addChild(then.description)
            }
        }
        return given.description
    }

    override fun runChild(child: Feature, notifier: RunNotifier) {
        // TODO: invoke actions
        val given = child.root
        given.children.forEach { `when` ->
            notifier.fireTestStarted(given.description)
            `when`.children.forEach { then ->
                notifier.fireTestStarted(`when`.description)

                notifier.fireTestStarted(then.description)
                notifier.fireTestFinished(then.description)

                notifier.fireTestFinished(`when`.description)
            }
            notifier.fireTestFinished(given.description)
        }
    }

    fun newInstance():T = testClass.onlyConstructor.newInstance() as T
}
