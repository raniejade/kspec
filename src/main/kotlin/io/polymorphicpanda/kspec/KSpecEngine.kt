package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.Context
import kspec.It
import kspec.KSpec
import kspec.Spec

class KSpecEngine<T: KSpec>(clazz: Class<T>): Spec {
    val root: Context = Context(clazz.name, {})
    var current = root

    override fun describe(description: String, action: () -> Unit) {
        enter(Context(format("describe", description), action))
        evaluate()
        exit()
    }

    override fun context(description: String, action: () -> Unit) {
        enter(Context(format("context", description), action))
        evaluate()
        exit()
    }

    override fun it(description: String, action: It.() -> Unit) {
        enter(Context(format("it", description), {
            action(object: It {})
        }, null, true))
        exit()
    }

    override fun before(action: () -> Unit) {
        current.before = action
    }

    override fun after(action: () -> Unit) {
        current.after = action
    }

    override fun beforeEach(action: () -> Unit) {
        current.beforeEach = action
    }

    override fun afterEach(action: () -> Unit) {
        current.afterEach = action
    }


    private fun format(prefix: String, description: String): String = "$prefix: $description"

    private fun enter(context: Context) {
        current.addChild(context)
        current = context
    }

    private fun evaluate() {
        current()
    }

    private fun exit() {
        current = current.parent ?: current
    }
}
