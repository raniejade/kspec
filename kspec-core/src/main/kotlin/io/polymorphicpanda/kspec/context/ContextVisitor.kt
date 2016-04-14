package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun preVisitExampleGroup(context: ExampleGroupContext): ContextVisitResult
    fun onVisitExample(context: ExampleContext): ContextVisitResult
    fun postVisitExampleGroup(context: ExampleGroupContext): ContextVisitResult
}
