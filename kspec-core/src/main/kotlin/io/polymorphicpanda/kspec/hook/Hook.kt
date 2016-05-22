package io.polymorphicpanda.kspec.hook

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.tag.Tag
import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
abstract class Hook(val tags: Set<KClass<out Tag<*>>>) {
    open fun handles(context: Context): Boolean {
        return tags.isEmpty() || context.containsAll(tags)
    }
}
