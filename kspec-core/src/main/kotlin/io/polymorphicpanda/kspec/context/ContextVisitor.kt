package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun preVisitExampleGroup(context: Context.ExampleGroup): ContextVisitResult
    fun onVisitExample(context: Context.Example): ContextVisitResult
    fun postVisitExampleGroup(context: Context.ExampleGroup): ContextVisitResult
}
