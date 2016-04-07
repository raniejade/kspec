package io.polymorphicpanda.kspec.context

import io.polymorphicpanda.kspec.tag.Tag
import java.util.*

open class Context(val description: String)

class ExampleGroupContext(description: String,
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
                    if (visitor.preVisitExampleGroup(context)) {
                        context.children.forEach { doVisit(visitor, it) }
                    }

                    visitor.postVisitExampleGroup(context)
                }
                is ExampleContext -> {
                    visitor.onVisitExample(context)
                }
            }
        }
    }
}

class ExampleContext(description: String, val parent: ExampleGroupContext,
                     val action: (() -> Unit)?, tags: Set<Tag> = setOf<Tag>())
    : Context(description) {

    val tags = HashSet<Tag>(tags)

    init {
        parent.children.add(this)
    }

    internal operator fun invoke() {
        parent.reset()
        action!!()
    }

    fun contains(tag: String): Boolean = tags.any { it.name == tag }

    operator fun get(tag: String): Tag? = tags.firstOrNull { it.name == tag }
}
