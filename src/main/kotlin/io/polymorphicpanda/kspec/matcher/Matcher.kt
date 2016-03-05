package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
interface Matcher<T> {
    fun match(arg: T)
}
