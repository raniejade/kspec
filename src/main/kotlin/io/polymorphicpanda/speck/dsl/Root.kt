package io.polymorphicpanda.speck.dsl

/**
 * @author Ranie Jade Ramiso
 */
interface Root: SpeckDsl {
    fun Given(description: String, clause: Given.() -> Unit)
}
