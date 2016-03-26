package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.ExampleGroupContext

class TestSpec: KSpec() {
    override fun spec() {

    }

}

fun setupTestSpec(block: Spec.() -> Unit): ExampleGroupContext {
    val spec: KSpec = TestSpec()
    with(spec, block)
    return spec.root
}
