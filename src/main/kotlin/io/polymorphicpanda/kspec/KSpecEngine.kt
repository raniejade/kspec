package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.Context
import kspec.KSpec
import kspec.Spec

class KSpecEngine<T: KSpec>(clazz: Class<T>): Spec {
    val root: Context = Context(clazz.name, {}, null)
    var current = root
    internal var disabled = false

    internal fun disable() {
        disabled = true
    }

    override fun specBlock(description: String, term: String?, terminal: Boolean, block: () -> Unit) {
        invokeIfNotDone {
            enter(format(term, description), { block()}, terminal)
            if (!terminal) {
                evaluate()
            }
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

    private fun format(prefix: String?, description: String): String {
        if (prefix == null) {
            return description
        }
        return "$prefix: $description"
    }

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
