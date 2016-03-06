package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.Context
import kspec.KSpec
import kspec.Spec

fun setupTestSpec(block: Spec.() -> Unit): Context {
    val engine = KSpecEngine(KSpec::class.java)
    with(engine, block)
    return engine.root
}
