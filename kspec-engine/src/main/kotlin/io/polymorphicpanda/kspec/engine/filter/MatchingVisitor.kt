package io.polymorphicpanda.kspec.engine.filter

import io.polymorphicpanda.kspec.context.*

/**
 * @author Ranie Jade Ramiso
 */
class MatchingVisitor(val predicate: (Context) -> Boolean): ContextVisitor {
    private var _matches = false

    val matches: Boolean
        get() = _matches

    override fun preVisitExampleGroup(context: ExampleGroupContext): ContextVisitResult {
        if (predicate(context)) {
            _matches = true
        }

        return ContextVisitResult.CONTINUE
    }

    override fun onVisitExample(context: ExampleContext): ContextVisitResult {
        if (predicate(context)) {
            _matches = true
        }
        return ContextVisitResult.CONTINUE
    }

    override fun postVisitExampleGroup(context: ExampleGroupContext) = ContextVisitResult.CONTINUE
}
