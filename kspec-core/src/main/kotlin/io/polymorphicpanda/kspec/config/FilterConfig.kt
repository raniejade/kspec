package io.polymorphicpanda.kspec.config

import io.polymorphicpanda.kspec.tag.Tag
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class FilterConfig {
    private val _includes = HashSet<Tag>()
    internal val includes: Set<Tag> = _includes

    private val _excludes = HashSet<Tag>()
    internal val excludes: Set<Tag> = _excludes

    private val _match = HashSet<Tag>()
    internal val match: Set<Tag> = _match


    fun include(vararg tags: Tag) {
        _includes.addAll(tags)
    }

    fun exclude(vararg tags: Tag) {
        _excludes.addAll(tags)
    }

    fun matching(vararg tags: Tag) {
        _match.addAll(tags)
    }
}
