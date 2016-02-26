package io.polymorphicpanda.speck

import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.runner.DslWalker
import io.polymorphicpanda.speck.runner.Feature
import io.polymorphicpanda.speck.runner.JUnitSpeckRunner
import org.junit.runner.RunWith

/**
 * @author Ranie Jade Ramiso
 */
@RunWith(JUnitSpeckRunner::class)
open class Speck(val init: Speck.() -> Unit) {
    internal lateinit var walker: DslWalker
    internal val features: MutableList<Feature> = mutableListOf()

    fun Given(description: String, init: Given.() -> Unit) {
        features.add(walker.walk(description, init))
    }
}
