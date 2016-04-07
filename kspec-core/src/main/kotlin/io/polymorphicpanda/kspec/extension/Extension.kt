package io.polymorphicpanda.kspec.extension

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ExampleGroupContext

/**
 * @author Ranie Jade Ramiso
 */
internal interface Extension {
    fun configure(config: KSpecConfig, root: ExampleGroupContext)
}
