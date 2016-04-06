package io.polymorphicpanda.kspec.extension

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ExampleGroupContext

/**
 * @author Ranie Jade Ramiso
 */
internal object CoreExtensions: Extension {
    val extensions = listOf(
            Pending
    )

    override fun configure(config: KSpecConfig, root: ExampleGroupContext) {
        extensions.forEach { it.configure(config, root) }
    }
}
