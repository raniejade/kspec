package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.context.GroupContext
import kspec.KSpec
import kspec.Spec
import kotlin.reflect.KClass

class KSpecEngine<T: KSpec>(clazz: Class<T>): Spec {
    val root: GroupContext = GroupContext(clazz.name, null)
    var current = root
    internal var disabled = false

    internal fun disable() {
        disabled = true
    }

    override fun group(description: String, term: String?, block: () -> Unit) {
        invokeIfNotDone {
            val context = GroupContext(format(term, description), current)
            current = context
            block()
            current = current.parent ?: current
        }
    }

    override fun example(description: String, term: String?, block: () -> Unit) {
        invokeIfNotDone {
            ExampleGroupContext(format(term, description), current, block)
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

    override fun <T: Any> group(subject: KClass<T>, description: String, term: String?, block: (() -> T) -> Unit) {
        invokeIfNotDone {
            val context = GroupContext(format(term, description), current) {
                val constructor =
                        subject.constructors.firstOrNull { it.parameters.isEmpty()} ?: throw IllegalArgumentException()
                constructor.call()
            }
            current = context
            block({ context.subject() as T })
            current = current.parent ?: current
        }
    }

    override fun <T> subject(block: () -> T) {
        invokeIfNotDone {
            current.subject = block
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
}
