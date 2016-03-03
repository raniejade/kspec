package io.polymorphicpanda.speck.dsl

import kotlin.reflect.KClass

interface Then: SpeckDsl {
    fun <T> shouldBeEqual(expected: T?, actual: T?)
    fun <T> shouldNotBeEqual(expected: T?, actual: T?)
    fun <T> shouldBeSame(expected: T?, actual: T?)
    fun <T> shouldNotBeSame(expected: T?, actual: T?)
    fun shouldBeTrue(expression: Boolean)
    fun shouldBeFalse(expression: Boolean)
    fun shouldThrow(invoke: () -> Unit)
    fun shouldThrow(expected: KClass<out Throwable>, invoke: () -> Unit)
}
