package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.Context
import kspec.It
import kspec.KSpec
import kspec.Spec

class KSpecEngine<T: KSpec>(clazz: Class<T>): Spec {
    val root: Context = Context(clazz.name, {}, null)
    var current = root
    internal var disabled = false

    internal fun disable() {
        disabled = true
    }

    override fun describe(description: String, action: () -> Unit) {
        invokeIfNotDone {
            enter(format("describe", description), { action() })
            evaluate()
            exit()
        }
    }

    override fun context(description: String, action: () -> Unit) {
        invokeIfNotDone {
            enter(format("context", description), { action() })
            evaluate()
            exit()
        }
    }

    override fun it(description: String, action: It.() -> Unit) {
        invokeIfNotDone {
            enter(format("it", description), {
                action(object: It {
                    override fun fail(message: String?) {
                        it.failure = AssertionError(message?: "explicitly marked to fail by user")
                    }
                })
            }, true)
            exit()
        }
    }

    override fun before(action: () -> Unit) {
        invokeIfNotDone {
            current.before = action
        }
    }

    override fun after(action: () -> Unit) {
        invokeIfNotDone {
            current.after = action
        }
    }

    override fun beforeEach(action: () -> Unit) {
        invokeIfNotDone {
            current.beforeEach = action
        }
    }

    override fun afterEach(action: () -> Unit) {
        invokeIfNotDone {
            current.afterEach = action
        }
    }

    private fun invokeIfNotDone(block: () -> Unit) {
        if (disabled) {
            throw CollectionException("You bad bad boy, this is not allowed here.")
        }
        block()
    }

    private fun format(prefix: String, description: String): String = "$prefix: $description"

    private fun enter(description: String, action: (Context) -> Unit, terminal: Boolean = false) {
        val context = Context(description, action, current, terminal)
        current = context
    }

    private fun evaluate() {
        current()
    }

    private fun exit() {
        current = current.parent ?: current
    }
}
