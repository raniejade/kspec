package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.config.KSpecConfig

/**
 * @author Ranie Jade Ramiso
 */
object StandardConfiguration: Configuration {
    override fun apply(config: KSpecConfig) {
        config.filter.matching(Focus::class)
        config.filter.ignore(Pending::class)
    }
}
