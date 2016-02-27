package io.polymorphicpanda.speck

import io.polymorphicpanda.speck.dsl.Root
import io.polymorphicpanda.speck.runner.JUnitSpeckRunner
import org.junit.runner.RunWith

/**
 * @author Ranie Jade Ramiso
 */
@RunWith(JUnitSpeckRunner::class)
open class Speck(val init: Root.() -> Unit) {
    internal operator fun invoke(walker: Root) {
        with(walker, init)
    }
}
