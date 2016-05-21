package io.polymorphicpanda.kspec.engine.execution

import io.polymorphicpanda.kspec.context.Context
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class ExecutionNotifier {
     val listeners = LinkedList<ExecutionListener>()

    fun addListener(listener: ExecutionListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: ExecutionListener) {
        listeners.remove(listener)
    }

    fun clearListeners() {
        listeners.clear()
    }

     fun notifyExampleGroupStarted(group: Context.ExampleGroup) {
        listeners.forEach { it.exampleGroupStarted(group) }
    }

     fun notifyExampleGroupFinished(group: Context.ExampleGroup, result: ExecutionResult) {
        listeners.forEach { it.exampleGroupFinished(group, result) }
    }

     fun notifyExampleStarted(example: Context.Example) {
        listeners.forEach { it.exampleStarted(example) }
    }

     fun notifyExampleFinished(example: Context.Example, result: ExecutionResult) {
        listeners.forEach { it.exampleFinished(example, result) }
    }

     fun notifyExecutionStarted() {
        listeners.forEach { it.executionStarted() }
    }

     fun notifyExecutionFinished() {
        listeners.forEach { it.executionFinished() }
    }

     fun notifyExampleGroupIgnored(group: Context.ExampleGroup) {
        listeners.forEach { it.exampleGroupIgnored(group) }
    }

     fun notifyExampleIgnored(example: Context.Example) {
        listeners.forEach { it.exampleIgnored(example) }
    }

}
