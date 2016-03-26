package io.polymorphicpanda.kspec.runner

import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext

/**
 * @author Ranie Jade Ramiso
 */
interface RunListener {
    fun exampleStarted(example: ExampleContext) {}
    fun exampleFailure(example: ExampleContext, failure: Throwable) {}
    fun exampleFinished(example: ExampleContext) {}
    fun exampleIgnored(example: ExampleContext) {}

    fun exampleGroupStarted(group: ExampleGroupContext) {}
    fun exampleGroupFailure(example: ExampleGroupContext, failure: Throwable) {}
    fun exampleGroupFinished(group: ExampleGroupContext) {}
}
