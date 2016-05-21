package io.polymorphicpanda.kspec.engine.filter

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitResult
import io.polymorphicpanda.kspec.context.ContextVisitor

/**
 * @author Ranie Jade Ramiso
 */
class MatchingVisitor(val predicate: (Context) -> Boolean): ContextVisitor {
    private var _matches = false

    val matches: Boolean
        get() = _matches

    override fun preVisitExampleGroup(context: Context.ExampleGroup): ContextVisitResult {
        if (predicate(context)) {
            _matches = true
        }

        return ContextVisitResult.CONTINUE
    }

    override fun onVisitExample(context: Context.Example): ContextVisitResult {
        if (predicate(context)) {
            _matches = true
        }
        return ContextVisitResult.CONTINUE
    }

    override fun postVisitExampleGroup(context: Context.ExampleGroup) = ContextVisitResult.CONTINUE
}
