package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
class ContentEquals<T>(expected: Collection<T>,
                       val strict: Boolean, message: Any?): SimpleMatcher<Collection<T>>(expected, message) {
    override fun doAssert(actual: Collection<T>, expected: Collection<T>): Boolean {
        if (strict) {
            return expected == actual
        }
        return expected.containsAll(actual)
    }
}
