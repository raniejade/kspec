package io.polymorphicpanda.kspec.tag

import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
abstract class Taggable(tags: Set<Tag<*>>) {
    abstract val parent: Taggable?

    val tags: Map<KClass<*>, Tag<*>> = tags.groupBy {
        it.javaClass.kotlin as KClass<*>
    }.mapValues { it.value.first() }

    fun <T: KClass<out Tag<*>>> contains(tags: Collection<T>): Boolean {
        return this.tags.any { tags.contains(it.key) }
            || parent?.contains(tags) ?: false
    }

    fun <T: KClass<out Tag<*>>> containsAll(tags: Collection<T>): Boolean {
        var result = false
        if (this.tags.isNotEmpty()) {
            result = this.tags.all { tags.contains(it.key) }
        }

        if (!result) {
            result = parent?.containsAll(tags) ?: false
        }

        return result
    }

    fun <T: KClass<out Tag<*>>> contains(vararg tags: T) = contains(setOf(*tags))

    inline fun <reified T: Tag<*>> get(): T? {
        if (tags.containsKey(T::class)) {
            return tags[T::class] as T
        } else if (parent != null && parent!!.tags.containsKey(T::class)) {
            return parent!!.tags[T::class] as T
        }

        return null
    }
}
