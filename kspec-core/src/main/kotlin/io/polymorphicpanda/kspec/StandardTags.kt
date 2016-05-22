package io.polymorphicpanda.kspec

import io.polymorphicpanda.kspec.tag.SimpleTag
import io.polymorphicpanda.kspec.tag.Tag


object Focus: SimpleTag()
class Pending(reason: String): Tag<String>(reason) {
    val reason: String
        get() = data
}

fun pending(reason: String) = Pending(reason)
fun focus() = Focus

