package io.polymorphicpanda.kspec.context

import io.polymorphicpanda.kspec.helpers.MemoizedHelper
import io.polymorphicpanda.kspec.tag.Tag
import io.polymorphicpanda.kspec.tag.Taggable
import java.util.*

sealed class Context(val description: String, parent: Taggable?, tags: Set<Tag>): Taggable(parent, tags) {
    class ExampleGroup(description: String,
                       val parent: ExampleGroup?,
                       tags: Set<Tag> = setOf<Tag>(),
                       var subjectFactory: () -> Any = { throw UnsupportedOperationException() })
    : Context(description, parent, tags) {
        val children = LinkedList<Context>()

        private var memoizedSubject = MemoizedHelper(subjectFactory)

        private val subjectInstance by memoizedSubject

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


        fun <T> subject() = subjectInstance as T

        internal fun reset() {
            parent?.reset()
            memoizedSubject.forget()
        }

        companion object {
            private fun doVisit(visitor: ContextVisitor, context: Context): ContextVisitResult {
                when(context) {
                    is ExampleGroup -> {
                        val result = visitor.preVisitExampleGroup(context)

                        if(result == ContextVisitResult.CONTINUE) {
                            val iterator = context.children.iterator()
                            while (iterator.hasNext()) {
                                val visitResult = doVisit(visitor, iterator.next())
                                if (visitResult == ContextVisitResult.TERMINATE) {
                                    return ContextVisitResult.TERMINATE
                                } else if (visitResult == ContextVisitResult.REMOVE) {
                                    iterator.remove()
                                }
                            }
                        }

                        if (result == ContextVisitResult.CONTINUE || result == ContextVisitResult.SKIP_SUBTREE) {
                            return visitor.postVisitExampleGroup(context)
                        }

                        return result
                    }
                }
                return visitor.onVisitExample(context as Example)
            }
        }
    }

    class Example(description: String, val parent: ExampleGroup,
                  val action: (() -> Unit)?, tags: Set<Tag> = setOf<Tag>())
    : Context(description, parent, tags) {

        init {
            parent.children.add(this)
        }

        fun execute() {
            parent.reset()
            action!!()
        }
    }

}
