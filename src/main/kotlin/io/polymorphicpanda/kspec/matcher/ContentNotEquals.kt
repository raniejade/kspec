package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
class ContentNotEquals<T>(expected: Collection<T>, message: String?): SimpleMatcher<Collection<T>>(expected, message) {
    override fun doAssert(actual: Collection<T>, expected: Collection<T>): Boolean {
        return !expected.containsAll(actual)
    }
}
