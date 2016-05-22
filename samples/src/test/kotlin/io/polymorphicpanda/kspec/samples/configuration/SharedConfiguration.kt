package io.polymorphicpanda.kspec.samples.configuration

import io.polymorphicpanda.kspec.Configuration
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.tag.SimpleTag

/**
 * @author Ranie Jade Ramiso
 */
class SharedConfiguration: Configuration {

    override fun apply(config: KSpecConfig) {
        config.filter.matching(Tag::class)
    }

    object Tag: SimpleTag()
}
