package io.polymorphicpanda.kspec.console.reporter

import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.launcher.reporter.BaseReporter
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Ranie Jade Ramiso
 */
class ConsoleReporter: BaseReporter() {

    private val logger by lazy(LazyThreadSafetyMode.NONE) {
        LoggerFactory.getLogger("io.polymorphicpanda.kspec.console.status")
    }

    private val counter = AtomicInteger()

    override fun exampleGroupSuccess(group: ExampleGroupContext) {
        super.exampleGroupSuccess(group)
        updateStatusLine()
    }

    override fun exampleSuccess(example: ExampleContext) {
        super.exampleSuccess(example)
        updateStatusLine()
        counter.andIncrement
    }

    override fun exampleGroupFailure(group: ExampleGroupContext, reason: Throwable) {
        super.exampleGroupFailure(group, reason)
        updateStatusLine()
    }

    override fun exampleFailure(example: ExampleContext, reason: Throwable) {
        super.exampleFailure(example, reason)
        updateStatusLine()
    }

    override fun exampleGroupIgnored(group: ExampleGroupContext) {
        super.exampleGroupIgnored(group)
        updateStatusLine()
    }

    override fun exampleIgnored(example: ExampleContext) {
        super.exampleIgnored(example)
        updateStatusLine()
    }

    override fun executionFinished() {
        super.executionFinished()
        logger.info("\n")
    }

    private fun updateStatusLine() {
        val status = "> ${counter.toInt()} spec(s) completed, " +
            "${totalFailureCount.toInt()} failed, ${totalIgnoredCount.toInt()} ignored."
        logger.info("\r$status")
    }
}
