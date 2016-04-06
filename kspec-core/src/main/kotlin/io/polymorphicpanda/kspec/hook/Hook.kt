package io.polymorphicpanda.kspec.hook

import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
abstract class Hook(val tags: Set<Tag>) {
    fun handles(example: ExampleContext): Boolean {
        return example.tags.containsAll(tags)
    }
}
