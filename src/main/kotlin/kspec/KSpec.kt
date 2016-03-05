package kspec

import io.polymorphicpanda.kspec.junit.KSpecRunner
import org.junit.runner.RunWith

@RunWith(KSpecRunner::class)
abstract class KSpec: Spec {
    var engine: Spec? = null

    abstract fun spec()

    override fun describe(description: String, action: () -> Unit) {
        engine!!.describe(description, action)
    }

    override fun context(description: String, action: () -> Unit) {
        engine!!.context(description, action)
    }

    override fun it(description: String, action: It.() -> Unit) {
        engine!!.it(description, action)
    }

    override fun before(action: () -> Unit) {
        engine!!.before(action)
    }

    override fun after(action: () -> Unit) {
        engine!!.after(action)
    }

    override fun beforeEach(action: () -> Unit) {
        engine!!.beforeEach(action)
    }

    override fun afterEach(action: () -> Unit) {
        engine!!.afterEach(action)
    }
}
