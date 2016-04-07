package io.polymorphicpanda.kspec.hook

import io.polymorphicpanda.kspec.context.ExampleContext

/**
 * @author Ranie Jade Ramiso
 */
abstract class Chain(val list: List<AroundHook>) {
    var iterator = list.iterator()

    fun next(example: ExampleContext) {
        iterator.next().execute(example, this)
    }

    abstract fun stop(reason: String)
}
