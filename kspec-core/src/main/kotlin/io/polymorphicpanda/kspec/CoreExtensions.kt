package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.extension.Extension
import io.polymorphicpanda.kspec.filter.Filter
import io.polymorphicpanda.kspec.filter.Focused
import io.polymorphicpanda.kspec.pending.Pending

/**
 * @author Ranie Jade Ramiso
 */
internal object CoreExtensions: Extension {
    val extensions = listOf(
            Focused,
            Pending,
            Filter
    )

    override fun configure(config: KSpecConfig, root: ExampleGroupContext) {
        extensions.forEach { it.configure(config, root) }
    }
}
