package io.polymorphicpanda.speck.dsl

interface When {
    fun Then(description: String, init: Then.() -> Unit)
}
