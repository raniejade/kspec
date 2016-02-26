package io.polymorphicpanda.speck.dsl

interface Then {
    fun <T> shouldBeEqual(expected: T?, actual: T?)
    fun <T> shouldNotBeEqual(expected: T?, actual: T?)
    fun <T> shouldBeSame(expected: T?, actual: T?)
    fun <T> shouldNotBeSame(expected: T?, actual: T?)
    fun shouldBeTrue(expression: Boolean)
    fun shouldBeFalse(expression: Boolean)
    fun shouldThrow(invoke: () -> Unit)
    fun shouldThrow(exception: Class<out Exception>, invoke: () -> Unit)
}
