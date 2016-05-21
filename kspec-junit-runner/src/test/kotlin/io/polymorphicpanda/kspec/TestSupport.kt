package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.Context

class TestSpec: KSpec() {
    override fun spec() {

    }

}

fun setupTestSpec(block: Spec.() -> Unit): Context.ExampleGroup {
    val spec: KSpec = TestSpec()
    with(spec, block)
    return spec.root
}
