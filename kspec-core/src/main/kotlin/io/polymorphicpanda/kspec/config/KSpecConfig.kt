package io.polymorphicpanda.kspec.config

import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.hook.AroundHook
import io.polymorphicpanda.kspec.hook.Chain
import io.polymorphicpanda.kspec.hook.SimpleHook
import io.polymorphicpanda.kspec.tag.Tag
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class KSpecConfig {
    private val _before = LinkedList<SimpleHook>()
    val before: List<SimpleHook> = _before

    private val _after = LinkedList<SimpleHook>()
    val after: List<SimpleHook> = _after

    private val _around = LinkedList<AroundHook>()
    val around: List<AroundHook> = _around

    fun before(vararg tags: Tag, block: (ExampleContext) -> Unit) {
        _before.add(SimpleHook(block, setOf(*tags)))
    }

    fun after(vararg tags: Tag, block: (ExampleContext) -> Unit) {
        _after.add(SimpleHook(block, setOf(*tags)))
    }

    fun around(vararg tags: Tag, block: (ExampleContext, Chain) -> Unit) {
        _around.add(AroundHook(block, setOf(*tags)))
    }

    fun clone(): KSpecConfig {
        val clone = KSpecConfig()

        clone._before.addAll(this.before)
        clone._after.addAll(this.after)
        clone._around.addAll(this.around)

        return clone
    }
}
