package io.polymorphicpanda.kspec.config

import io.polymorphicpanda.kspec.tag.Tag
import java.util.*
import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
class FilterConfig {
    private val _includes = HashSet<KClass<out Tag<*>>>()
    val includes: Set<KClass<out Tag<*>>> = _includes

    private val _excludes = HashSet<KClass<out Tag<*>>>()
    val excludes: Set<KClass<out Tag<*>>> = _excludes

    private val _ignore = HashSet<KClass<out Tag<*>>>()
    val ignore: Set<KClass<out Tag<*>>> = _ignore

    private val _match = HashSet<KClass<out Tag<*>>>()
    val match: Set<KClass<out Tag<*>>> = _match


    fun include(vararg tags: KClass<out Tag<*>>) {
        _includes.addAll(tags)
    }

    fun exclude(vararg tags: KClass<out Tag<*>>) {
        _excludes.addAll(tags)
    }

    fun ignore(vararg tags: KClass<out Tag<*>>) {
        _ignore.addAll(tags)
    }

    fun matching(vararg tags: KClass<out Tag<*>>) {
        _match.addAll(tags)
    }

    fun copy(source: FilterConfig) {
        this._includes.addAll(source.includes)
        this._excludes.addAll(source.excludes)
        this._match.addAll(source.match)
    }

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as FilterConfig

        if (includes != other.includes) return false
        if (excludes != other.excludes) return false
        if (match != other.match) return false

        return true
    }

    override fun hashCode(): Int{
        var result = includes.hashCode()
        result += 31 * result + excludes.hashCode()
        result += 31 * result + match.hashCode()
        return result
    }


}
