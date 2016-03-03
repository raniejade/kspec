package io.polymorphicpanda.speck.dsl

interface When: SpeckDsl {
    fun Then(description: String, clause: Then.() -> Unit)
}
