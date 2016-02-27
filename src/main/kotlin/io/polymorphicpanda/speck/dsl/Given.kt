package io.polymorphicpanda.speck.dsl

interface Given {
    fun BeforeWhen(action: (String) -> Unit)
    fun When(description: String, init: When.() -> Unit)
    fun AfterWhen(action: (String) -> Unit)
}
