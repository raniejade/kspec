package io.polymorphicpanda.kspec.pending

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.extension.Extension
import io.polymorphicpanda.kspec.tag.Ignored
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
internal object Pending: Extension {
    const val TAG_NAME = "pending"

    fun tag(reason: String = ""): Tag {
        return Ignored(TAG_NAME, reason)
    }

    override fun configure(config: KSpecConfig, root: ExampleGroupContext) {
        config.filter.exclude(tag())
    }
}
