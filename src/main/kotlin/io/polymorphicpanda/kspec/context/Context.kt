package io.polymorphicpanda.kspec.context

import java.util.*

open class Context(val description: String)

open class GroupContext(description: String,
                        val parent: GroupContext?,
                        var subjectFactory: () -> Any? = { throw UnsupportedOperationException() })
    : Context(description) {
    internal val children = LinkedList<Context>()

    private var subjectInstance: Any? = null

    var before: (() -> Unit)? = null
    var after: (() -> Unit)? = null

    var beforeEach: (() -> Unit)? = null
    var afterEach: (() -> Unit)? = null

    init {
        if (parent != null) {
            parent.children.add(this)
        }
    }

    fun visit(visitor: ContextVisitor) {
        doVisit(visitor, this)
    }

    fun <T> subject(): T {
        if (subjectInstance == null) {
            subjectInstance = subjectFactory()
        }
        return subjectInstance as T
    }

    fun reset() {
        parent?.reset()
        subjectInstance = null
    }


    companion object {
        private fun doVisit(visitor: ContextVisitor, context: Context) {
            when(context) {
                is GroupContext -> {
                    visitor.preVisitGroup(context)
                    visitor.onVisitGroup(context)
                    context.children.forEach { doVisit(visitor, it) }
                    visitor.postVisitGroup(context)
                }
                is ExampleGroupContext -> {
                    visitor.preVisitExampleGroup(context)
                    visitor.onVisitExampleGroup(context)
                    visitor.postVisitExampleGroup(context)
                }
            }
        }
    }
}

class ExampleGroupContext(description: String, val parent: GroupContext, val action: () -> Unit)
    : Context(description) {
    init {
        parent.children.add(this)
    }

    operator fun invoke() {
        parent.reset()
        action()
    }
}
