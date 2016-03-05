package io.polymorphicpanda.kspec.matcher

class Expect<T>(val arg: T) {
    fun <K: Matcher<in T>> toBe(matcher: K) {
        matcher.match(arg)
    }
}
