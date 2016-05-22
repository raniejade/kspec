package io.polymorphicpanda.kspec.tag

abstract class Tag<T>(protected val data: T)
abstract class SimpleTag: Tag<Unit?>(null)
