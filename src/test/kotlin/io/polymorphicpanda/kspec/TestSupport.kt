package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.context.GroupContext
import kspec.KSpec
import kspec.Spec

fun setupTestSpec(block: Spec.() -> Unit): GroupContext {
    val engine = KSpecEngine(KSpec::class.java)
    with(engine, block)
    return engine.root
}
