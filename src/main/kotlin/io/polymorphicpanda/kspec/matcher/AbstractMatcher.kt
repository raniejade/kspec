package io.polymorphicpanda.kspec.matcher

abstract class AbstractMatcher<T>(val message: Any?): Matcher<T> {
    companion object {
        fun fail(message: Any? = null) {
            if (message == null) {
                throw AssertionError()
            }
            throw AssertionError(message)
        }
    }
}
