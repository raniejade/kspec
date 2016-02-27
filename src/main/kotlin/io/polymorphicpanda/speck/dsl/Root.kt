package io.polymorphicpanda.speck.dsl

/**
 * @author Ranie Jade Ramiso
 */
interface Root {
    fun Given(description: String, init: Given.() -> Unit)
}
