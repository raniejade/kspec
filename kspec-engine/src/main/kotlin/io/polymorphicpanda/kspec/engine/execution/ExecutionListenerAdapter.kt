package io.polymorphicpanda.kspec.engine.execution

import io.polymorphicpanda.kspec.context.Context

/**
 * @author Ranie Jade Ramiso
 */
open class ExecutionListenerAdapter: ExecutionListener {
    override fun executionStarted() { }

    override fun executionFinished() { }

    override fun exampleStarted(example: Context.Example) { }

    override fun exampleIgnored(example: Context.Example) { }

    override fun exampleFinished(example: Context.Example, result: ExecutionResult) { }

    override fun exampleGroupStarted(group: Context.ExampleGroup) { }

    override fun exampleGroupIgnored(group: Context.ExampleGroup) { }

    override fun exampleGroupFinished(group: Context.ExampleGroup, result: ExecutionResult) { }
}
