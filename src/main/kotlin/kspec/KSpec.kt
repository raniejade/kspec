package kspec

import io.polymorphicpanda.kspec.junit.KSpecRunner
import org.junit.runner.RunWith
import kotlin.reflect.KClass

@RunWith(KSpecRunner::class)
abstract class KSpec: Spec {
    lateinit var engine: Spec

    abstract fun spec()

    override fun before(action: () -> Unit) {
        engine.before(action)
    }

    override fun after(action: () -> Unit) {
        engine.after(action)
    }

    override fun beforeEach(action: () -> Unit) {
        engine.beforeEach(action)
    }

    override fun afterEach(action: () -> Unit) {
        engine.afterEach(action)
    }

    override fun group(description: String, block: () -> Unit) {
        engine.group(description, block)
    }

    override fun example(description: String, block: () -> Unit) {
        engine.example(description, block)
    }

    override fun pendingExample(description: String, reason: String?, block: (() -> Unit)?) {
        engine.pendingExample(description, reason, block)
    }

    override fun <T: Any> group(subject: KClass<T>, description: String,
                                block: SubjectSpec<T>.() -> Unit) {
        engine.group(subject, description, block)
    }
}
