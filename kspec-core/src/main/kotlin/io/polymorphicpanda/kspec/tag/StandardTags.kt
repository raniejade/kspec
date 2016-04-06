package io.polymorphicpanda.kspec.tag

import io.polymorphicpanda.kspec.extension.Pending

fun pending(reason: String) = Pending.tag(reason)
