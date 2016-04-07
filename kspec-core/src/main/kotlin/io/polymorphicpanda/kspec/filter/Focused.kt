package io.polymorphicpanda.kspec.filter

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.extension.Extension
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
object Focused: Extension {
    val tag = Tag("focus")

    override fun configure(config: KSpecConfig, root: ExampleGroupContext) {
        config.filter.matching(tag)
    }
}
