package io.polymorphicpanda.kspec.extensions

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
internal object Pending {
    const val TAG_NAME = "pending"
    const val DATA_REASON = "reason"

    fun tag(reason: String): Tag {
        return Tag(TAG_NAME, mapOf(DATA_REASON to reason))
    }

    fun configure(config: KSpecConfig) {
        config.around { example, run, ignore ->
            val tag = example[TAG_NAME];

            if (tag != null) {
                val reason: String = tag[DATA_REASON];
                ignore("Pending: $reason")
            } else {
                run()
            }
        }
    }
}

fun pending(reason: String) = Pending.tag(reason)
