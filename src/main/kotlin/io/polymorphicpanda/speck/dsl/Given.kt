package io.polymorphicpanda.speck.dsl

interface Given {
    fun When(description: String, init: When.() -> Unit)
}
