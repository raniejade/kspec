package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.KSpecEngine
import kspec.KSpec
import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.notification.RunNotifier

class KSpecRunner<T: KSpec>(clazz: Class<T>): Runner() {
    val engine = KSpecEngine(clazz)
    val describer = JUnitTestDescriber()
    val _description by lazy(LazyThreadSafetyMode.NONE) {
        val spec = clazz.newInstance()
        spec.engine = engine
        spec.spec()
        engine.root.visit(describer)
        engine.disable()
        describer.contextDescriptions[engine.root]!!
    }

    override fun run(notifier: RunNotifier?) {
        val executor = JUnitTestExecutor(notifier!!, describer.contextDescriptions)
        engine.root.visit(executor)
    }

    override fun getDescription(): Description = _description
}
