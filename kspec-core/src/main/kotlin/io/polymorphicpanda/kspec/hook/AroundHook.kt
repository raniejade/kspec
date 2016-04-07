package io.polymorphicpanda.kspec.hook

import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
class AroundHook(val block: (ExampleContext, Chain) -> Unit, tags: Set<Tag>): Hook(tags) {
    fun execute(example: ExampleContext, chain: Chain) {
        block(example, chain)
    }
}
