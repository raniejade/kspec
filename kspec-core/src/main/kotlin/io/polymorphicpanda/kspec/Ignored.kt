package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
object Ignored {
    const val TAG_NAME = "ignored"

    fun tag(reason: String) = Tag(TAG_NAME, mapOf("reason" to  reason))
}
