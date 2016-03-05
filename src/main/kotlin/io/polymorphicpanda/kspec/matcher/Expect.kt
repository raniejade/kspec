package io.polymorphicpanda.kspec.matcher

class Expect<T, K: Matcher<T>>(val arg: T) {
    fun to(matcher: K) {
        matcher.match(arg)
    }
}
