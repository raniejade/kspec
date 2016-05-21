package io.polymorphicpanda.kspec.launcher.reporter

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.engine.execution.ExecutionResult

/**
 * @author Ranie Jade Ramiso
 */
interface Reporter {
    fun executionStarted()
    fun executionFinished()

    fun exampleGroupFinished(group: Context.ExampleGroup, result: ExecutionResult)
    fun exampleFinished(example: Context.Example, result: ExecutionResult)

    fun exampleGroupIgnored(group: Context.ExampleGroup)
    fun exampleIgnored(example: Context.Example)
}
