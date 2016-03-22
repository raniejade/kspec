package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun preVisitGroup(context: GroupContext) {}
    fun onVisitGroup(context: GroupContext) {}
    fun postVisitGroup(context: GroupContext) {}

    fun preVisitExampleGroup(context: ExampleGroupContext) {}
    fun onVisitExampleGroup(context: ExampleGroupContext) {}
    fun postVisitExampleGroup(context: ExampleGroupContext) {}
}
