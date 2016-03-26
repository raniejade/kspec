package io.polymorphicpanda.kspec.context

import java.util.*

open class Context(val description: String)

open class ExampleGroupContext(description: String,
                               val parent: ExampleGroupContext?,
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
                is ExampleGroupContext -> {
                    visitor.preVisitExampleGroup(context)
                    visitor.onVisitExampleGroup(context)
                    context.children.forEach { doVisit(visitor, it) }
                    visitor.postVisitExampleGroup(context)
                }
                is ExampleContext -> {
                    visitor.preVisitExample(context)
                    visitor.onVisitExample(context)
                    visitor.postVisitExample(context)
                }
            }
        }
    }
}

class ExampleContext(description: String, val parent: ExampleGroupContext,
                     val action: (() -> Unit)?, val reason: String = "")
    : Context(description) {
    init {
        parent.children.add(this)
    }

    val pending: Boolean
        get() = action == null

    operator fun invoke() {
        if (!pending) {
            parent.reset()
            action!!()
        }
    }
}
