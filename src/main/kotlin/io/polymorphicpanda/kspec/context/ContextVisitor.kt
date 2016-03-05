package io.polymorphicpanda.kspec.context

interface ContextVisitor {
    fun pre(context: Context) {}
    fun on(context: Context) {}
    fun post(context: Context) {}
}
