package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun preVisitGroup(context: ExampleGroupContext) {}
    fun onVisitGroup(context: ExampleGroupContext) {}
    fun postVisitGroup(context: ExampleGroupContext) {}

    fun preVisitExampleGroup(context: ExampleContext) {}
    fun onVisitExampleGroup(context: ExampleContext) {}
    fun postVisitExampleGroup(context: ExampleContext) {}
}
