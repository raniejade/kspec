package io.polymorphicpanda.speck.dsl

/**
 * @author Ranie Jade Ramiso
 */
interface Spec {
    fun Given(description: String, init: Given.() -> Unit)
}
