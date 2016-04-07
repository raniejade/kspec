package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun preVisitExampleGroup(context: ExampleGroupContext): Boolean
    fun postVisitExampleGroup(context: ExampleGroupContext)

    fun onVisitExample(context: ExampleContext)
}
