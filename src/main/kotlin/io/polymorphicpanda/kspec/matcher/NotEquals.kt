package io.polymorphicpanda.kspec.matcher

class NotEquals<T>(expected: T, message: Any?): SimpleMatcher<T>(expected, message) {
    override fun doAssert(actual: T?, expected: T): Boolean = actual != expected

}
