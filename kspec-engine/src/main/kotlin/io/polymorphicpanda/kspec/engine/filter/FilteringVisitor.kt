package io.polymorphicpanda.kspec.engine.filter

import io.polymorphicpanda.kspec.context.*
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class FilteringVisitor(val predicate: (Context) -> Boolean): ContextVisitor {
    val matched = HashSet<Context>()

    override fun preVisitExampleGroup(context: ExampleGroupContext): ContextVisitResult {
        return ContextVisitResult.CONTINUE
    }

    override fun onVisitExample(context: ExampleContext): ContextVisitResult {
        if (predicate(context)) {
            addRecursive(context)
            return ContextVisitResult.CONTINUE
        }
        return ContextVisitResult.REMOVE
    }

    override fun postVisitExampleGroup(context: ExampleGroupContext): ContextVisitResult {
        if (matched.contains(context) || predicate(context)) {
            return ContextVisitResult.CONTINUE
        }
        return ContextVisitResult.REMOVE
    }

    private fun addRecursive(context: Context) {
        matched.add(context)
        when(context) {
            is ExampleContext -> {
                addRecursive(context.parent)
            }
            is ExampleGroupContext -> {
                if (context.parent != null) {
                    addRecursive(context.parent!!)
                }
            }
        }
    }
}
