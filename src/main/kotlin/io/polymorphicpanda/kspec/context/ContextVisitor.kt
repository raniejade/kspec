package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun preVisitExampleGroup(context: ExampleGroupContext) {}
    fun onVisitExampleGroup(context: ExampleGroupContext) {}
    fun postVisitExampleGroup(context: ExampleGroupContext) {}

    fun preVisitExample(context: ExampleContext) {}
    fun onVisitExample(context: ExampleContext) {}
    fun postVisitExample(context: ExampleContext) {}
}
