package io.polymorphicpanda.kspec.matcher

abstract class SimpleMatcher<T>(val expected: T, message: Any?): AbstractMatcher<T>(message) {
    override fun match(arg: T?) {
        assert(doAssert(arg, expected))
    }

    protected abstract fun doAssert(actual: T?, expected: T): Boolean
}
