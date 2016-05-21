package io.polymorphicpanda.kspec.launcher.reporter

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.engine.execution.ExecutionResult

/**
 * @author Ranie Jade Ramiso
 */
open class ReporterAdapter: Reporter {
    override fun executionStarted() { }

    override fun executionFinished() { }

    override fun exampleGroupFinished(group: Context.ExampleGroup, result: ExecutionResult) { }

    override fun exampleFinished(example: Context.Example, result: ExecutionResult) { }

    override fun exampleGroupIgnored(group: Context.ExampleGroup) { }

    override fun exampleIgnored(example: Context.Example) { }
}
