package io.polymorphicpanda.speck

import io.polymorphicpanda.speck.dsl.Spec
import io.polymorphicpanda.speck.runner.JUnitSpeckRunner
import org.junit.runner.RunWith

/**
 * @author Ranie Jade Ramiso
 */
@RunWith(JUnitSpeckRunner::class)
open class Speck(val init: Spec.() -> Unit) {
    internal operator fun invoke(walker: Spec) {
        with(walker, init)
    }
}
