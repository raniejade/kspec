package io.polymorphicpanda.kspec.matcher

abstract class AbstractMatcher<T>(val message: Any?): Matcher<T> {
    protected fun test(expression: Boolean) {
        if (message != null) {
            assert(expression, { message })
        } else {
            assert(expression)
        }
    }

    companion object {
        fun fail(message: Any? = null) {
            if (message == null) {
                throw AssertionError()
            }
            throw AssertionError(message)
        }
    }
}
