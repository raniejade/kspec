package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.Context

class TestSpec: KSpec() {
    override fun spec() {
        throw UnsupportedOperationException()
    }
}

inline fun setupSpec(block: Spec.() -> Unit): Context.ExampleGroup {
    val builder = TestSpec()
    block.invoke(builder)
    return builder.root
}
