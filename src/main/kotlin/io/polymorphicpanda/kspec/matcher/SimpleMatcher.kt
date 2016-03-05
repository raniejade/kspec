package io.polymorphicpanda.kspec.matcher

import junit.framework.ComparisonFailure

abstract class SimpleMatcher<T>(val expected: T, message: Any?): AbstractMatcher<T>(message) {
    override fun match(arg: T?) {
        test(doAssert(arg, expected)) {
            val desc = if (message != null) {
                "$message"
            } else {
                "expected:<[$expected]> but was:<[$arg]>"
            }
            ComparisonFailure(desc, "$expected", "$arg")
        }
    }

    protected fun test(expression: Boolean, block: () -> Throwable) {
        if (!expression) {
            throw block()
        }
    }

    protected abstract fun doAssert(actual: T?, expected: T): Boolean
}
