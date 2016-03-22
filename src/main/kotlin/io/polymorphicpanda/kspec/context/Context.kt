package io.polymorphicpanda.kspec.context

import java.util.*

class Context(var description: String, var action: (Context) -> Unit,
              val parent: Context?, val example: Boolean = false) {

    private val _children = LinkedList<Context>()

    init {
        if (parent != null) {
            parent._children.add(this)
        }
    }


    var failure: Throwable? = null
    var children: List<Context> = _children
    var before: (() -> Unit)? = null
    var after: (() -> Unit)? = null

    var beforeEach: (() -> Unit)? = null
    var afterEach: (() -> Unit)? = null

    fun visit(visitor: ContextVisitor) {
        doVisit(visitor, this)
    }

    operator fun invoke() {
        action(this)
    }

    companion object {
        private fun doVisit(visitor: ContextVisitor, context: Context) {
            visitor.pre(context)
            visitor.on(context)
            if (!context.example) {
                context.children.forEach { doVisit(visitor, it) }
            }
            visitor.post(context)
        }
    }

}
