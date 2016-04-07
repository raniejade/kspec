package io.polymorphicpanda.kspec.tag

import io.polymorphicpanda.kspec.filter.Focused
import io.polymorphicpanda.kspec.pending.Pending

fun pending(reason: String) = Pending.tag(reason)
fun focus() = Focused.tag
