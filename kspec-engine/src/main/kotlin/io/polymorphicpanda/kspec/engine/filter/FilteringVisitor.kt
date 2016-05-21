package io.polymorphicpanda.kspec.engine.filter

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitResult
import io.polymorphicpanda.kspec.context.ContextVisitor
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class FilteringVisitor(val predicate: (Context) -> Boolean): ContextVisitor {
    val matched = HashSet<Context>()

    override fun preVisitExampleGroup(context: Context.ExampleGroup): ContextVisitResult {
        return ContextVisitResult.CONTINUE
    }

    override fun onVisitExample(context: Context.Example): ContextVisitResult {
        if (predicate(context)) {
            addRecursive(context)
            return ContextVisitResult.CONTINUE
        }
        return ContextVisitResult.REMOVE
    }

    override fun postVisitExampleGroup(context: Context.ExampleGroup): ContextVisitResult {
        if (matched.contains(context) || predicate(context)) {
            return ContextVisitResult.CONTINUE
        }
        return ContextVisitResult.REMOVE
    }

    private fun addRecursive(context: Context) {
        matched.add(context)
        when(context) {
            is Context.Example -> {
                addRecursive(context.parent)
            }
            is Context.ExampleGroup -> {
                if (context.parent != null) {
                    addRecursive(context.parent!!)
                }
            }
        }
    }
}
