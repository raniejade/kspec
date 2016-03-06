package io.polymorphicpanda.kspec.matcher

class Same<T>(expected: T, message: String?): SimpleMatcher<T>(expected, message) {
    override fun doAssert(actual: T, expected: T): Boolean = actual === expected
}
