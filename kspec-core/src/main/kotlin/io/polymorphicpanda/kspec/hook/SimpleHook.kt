package io.polymorphicpanda.kspec.hook

import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
class SimpleHook(val block: (ExampleContext) -> Unit, tags: Set<Tag>): Hook(tags) {
    fun execute(example: ExampleContext) {
        block(example)
    }
}
