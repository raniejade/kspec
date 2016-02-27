package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.Speck
import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runners.ParentRunner

/**
 * @author Ranie Jade Ramiso
 */
internal class JUnitSpeckRunner<T: Speck>(testClass: Class<T>): ParentRunner<GivenRunner>(testClass) {
    override fun getChildren(): List<GivenRunner> {
        val speck = newInstance()
        val walker = FeatureCollector()
        speck(walker)
        return walker.roots.map { GivenRunner(testClass.javaClass, it) }
    }

    override fun describeChild(child: GivenRunner): Description {
        return child.description
    }

    override fun runChild(child: GivenRunner, notifier: RunNotifier) {
        child.run(notifier)
    }

    @Suppress("UNCHECKED_CAST")
    fun newInstance():T = testClass.onlyConstructor.newInstance() as T
}
