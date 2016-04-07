package io.polymorphicpanda.kspec.pending

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.extension.Extension
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
internal object Pending: Extension {
    const val TAG_NAME = "pending"
    const val DATA_REASON = "reason"

    fun tag(reason: String): Tag {
        return Tag(TAG_NAME, mapOf(DATA_REASON to reason))
    }

    override fun configure(config: KSpecConfig, root: ExampleGroupContext) {
        config.around { example, chain ->
            val tag = example[TAG_NAME];

            if (tag != null) {
                val reason: String = tag[DATA_REASON];
                chain.stop("Pending: $reason")
            } else {
                chain.next(example)
            }
        }
    }
}
