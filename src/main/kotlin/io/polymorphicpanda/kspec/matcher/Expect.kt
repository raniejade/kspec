package io.polymorphicpanda.kspec.matcher

class Expect<T>(val arg: T) {
    fun toBe(matcher: Matcher<T>) {
        matcher.match(arg)
    }
}
