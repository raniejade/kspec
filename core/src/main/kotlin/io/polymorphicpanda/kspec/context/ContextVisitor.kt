package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun preVisitExampleGroup(context: ExampleGroupContext): Boolean
    fun onVisitExampleGroup(context: ExampleGroupContext)
    fun postVisitExampleGroup(context: ExampleGroupContext)

    fun preVisitExample(context: ExampleContext): Boolean
    fun onVisitExample(context: ExampleContext)
    fun postVisitExample(context: ExampleContext)
}
