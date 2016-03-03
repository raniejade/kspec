package io.polymorphicpanda.speck.dsl

interface Given: SpeckDsl {
    fun Before(action: (String) -> Unit)
    fun When(description: String, clause: When.() -> Unit)
    fun After(action: (String) -> Unit)
}
