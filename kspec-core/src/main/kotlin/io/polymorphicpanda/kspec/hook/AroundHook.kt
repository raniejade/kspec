package io.polymorphicpanda.kspec.hook

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.tag.Tag
import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
class AroundHook(val block: (Context, Chain) -> Unit, tags: Set<KClass<out Tag<*>>>): Hook(tags) {
    fun execute(context: Context, chain: Chain) {
        block(context, chain)
    }
}
