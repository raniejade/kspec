package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
interface Matcher<in T> {
    fun match(arg: T)
}
