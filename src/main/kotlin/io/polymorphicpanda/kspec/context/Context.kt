package io.polymorphicpanda.kspec.context

import java.util.*

class Context(var description: String, var action: () -> Unit,
              var parent: Context? = null, val terminal: Boolean = false) {
    private val _children = LinkedList<Context>()
    var children: List<Context> = _children
    var before: (() -> Unit)? = null
    var after: (() -> Unit)? = null

    var beforeEach: (() -> Unit)? = null
    var afterEach: (() -> Unit)? = null

    fun addChild(context: Context) {
        context.parent = this
        _children.add(context)
    }

    fun visit(visitor: ContextVisitor) {
        doVisit(visitor, this)
    }

    operator fun invoke() {
        action()
    }

    companion object {
        private fun doVisit(visitor: ContextVisitor, context: Context) {
            visitor.pre(context)
            visitor.on(context)
            if (!context.terminal) {
                context.children.forEach { doVisit(visitor, it) }
            }
            visitor.post(context)
        }
    }

}
