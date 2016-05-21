package io.polymorphicpanda.kspec.console.reporter

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.engine.execution.ExecutionResult
import io.polymorphicpanda.kspec.launcher.reporter.BaseReporter
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Ranie Jade Ramiso
 */
class ConsoleReporter: BaseReporter() {

    private val statusLogger by lazy(LazyThreadSafetyMode.NONE) {
        LoggerFactory.getLogger("io.polymorphicpanda.kspec.console.status")
    }

    private val errorLogger by lazy(LazyThreadSafetyMode.NONE) {
        LoggerFactory.getLogger("io.polymorphicpanda.kspec.console.info")
    }

    private val counter = AtomicInteger()

    override fun exampleGroupFinished(group: Context.ExampleGroup, result: ExecutionResult) {
        super.exampleGroupFinished(group, result)
        updateConsole {  }
    }

    override fun exampleFinished(example: Context.Example, result: ExecutionResult) {
        super.exampleFinished(example, result)
        counter.andIncrement
        updateConsole {
            if (result is ExecutionResult.Failure) {
                errorLogger.error(prettify(example, countParent(example)), result.cause)
            }
        }
    }

    override fun exampleGroupIgnored(group: Context.ExampleGroup) {
        super.exampleGroupIgnored(group)
        updateConsole {  }
    }

    override fun exampleIgnored(example: Context.Example) {
        super.exampleIgnored(example)
        updateConsole {  }
    }

    override fun executionFinished() {
        super.executionFinished()
        statusLogger.info("\n")
    }

    private inline  fun updateConsole(block: () -> Unit) {
        resetStatusLine()
        block()
        updateStatusLine()
    }

    private fun updateStatusLine() {
        val status = "> ${counter.toInt()} spec(s) completed, " +
            "${totalFailureCount.toInt()} failed, ${totalIgnoredCount.toInt()} ignored."
        statusLogger.info("$status")
    }

    private fun resetStatusLine() {
        statusLogger.info("\r")
    }

    private fun prettify(context: Context, depth: Int): String {
        return when(context) {
            is Context.Example -> "${prettify(context.parent, depth - 1)}${" ".repeat(depth * 2)}${context.description}"
            is Context.ExampleGroup -> {
                return if(context.parent != null) {
                    "${prettify(context.parent!!, depth - 1)}${" ".repeat(depth * 2)}${context.description}\n"
                } else {
                    "${context.description}\n"
                }
            }
            else -> ""
        }
    }

    private fun countParent(context: Context): Int {
        return when(context) {
            is Context.Example -> countParent(context.parent)
            is Context.ExampleGroup -> {
                return if (context.parent != null) {
                    countParent(context.parent!!) + 1
                } else {
                    1
                }
            }
            else -> 0
        }
    }
}
