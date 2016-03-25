package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.ExampleGroupContext
import kspec.KSpec
import kspec.Spec

fun setupTestSpec(block: Spec.() -> Unit): ExampleGroupContext {
    val engine = KSpecEngine(KSpec::class.java)
    with(engine, block)
    return engine.root
}
