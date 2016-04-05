package io.polymorphicpanda.kspec.config

import io.polymorphicpanda.kspec.context.ExampleContext
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class KSpecConfig {
    private val _before = LinkedList<(ExampleContext) -> Unit>()
    val before: List<(ExampleContext) -> Unit> = _before

    private val _after = LinkedList<(ExampleContext) -> Unit>()
    val after: List<(ExampleContext) -> Unit> = _after

    private val _around = LinkedList<(ExampleContext, () -> Unit, (String) -> Unit) -> Unit>()
    val around: List<(ExampleContext, () -> Unit, (String) -> Unit) -> Unit> = _around

    fun before(block: (ExampleContext) -> Unit) {
        _before.add(block)
    }

    fun after(block: (ExampleContext) -> Unit) {
        _after.add(block)
    }

    fun around(block: (ExampleContext, () -> Unit, (String) -> Unit) -> Unit) {
        _around.add(block)
    }

    fun clone(): KSpecConfig {
        val clone = KSpecConfig()

        clone._before.addAll(this.before)
        clone._after.addAll(this.after)
        clone._around.addAll(this.around)

        return clone
    }
}
