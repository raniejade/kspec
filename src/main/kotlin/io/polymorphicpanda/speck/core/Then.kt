package io.polymorphicpanda.speck.core

import io.polymorphicpanda.speck.dsl.Then

internal class ThenImpl : Then {
    override fun <T> shouldBeEqual(expected: T?, actual: T?) {
        throw UnsupportedOperationException()
    }

    override fun <T> shouldNotBeEqual(expected: T?, actual: T?) {
        throw UnsupportedOperationException()
    }

    override fun <T> shouldBeSame(expected: T?, actual: T?) {
        throw UnsupportedOperationException()
    }

    override fun <T> shouldNotBeSame(expected: T?, actual: T?) {
        throw UnsupportedOperationException()
    }

    override fun shouldBeTrue(expression: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun shouldBeFalse(expression: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun shouldThrow(invoke: () -> Unit) {
        throw UnsupportedOperationException()
    }

    override fun shouldThrow(exception: Class<out Exception>, invoke: () -> Unit) {
        throw UnsupportedOperationException()
    }
}
