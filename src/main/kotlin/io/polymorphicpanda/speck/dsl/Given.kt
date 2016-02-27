package io.polymorphicpanda.speck.dsl

interface Given {
    fun BeforeWhen(action: (String) -> Unit)
    fun When(description: String, given: When.() -> Unit)
    fun AfterWhen(action: (String) -> Unit)
}
