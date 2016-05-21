package io.polymorphicpanda.kspec.engine.execution

import io.polymorphicpanda.kspec.context.Context

/**
 * @author Ranie Jade Ramiso
 */
interface ExecutionListener {
    fun executionStarted()
    fun executionFinished()

    fun exampleStarted(example: Context.Example)
    fun exampleIgnored(example: Context.Example)
    fun exampleFinished(example: Context.Example, result: ExecutionResult)

    fun exampleGroupStarted(group: Context.ExampleGroup)
    fun exampleGroupIgnored(group: Context.ExampleGroup)
    fun exampleGroupFinished(group: Context.ExampleGroup, result: ExecutionResult)
}
