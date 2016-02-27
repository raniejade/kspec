package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.execution.ThenExecutor
import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.notification.RunNotifier

/**
 * @author Ranie Jade Ramiso
 */
internal class ThenRunner(val then: Node<Then>): Runner() {
    override fun run(notifier: RunNotifier) {
        notifier.fireTestStarted(description)
        with(ThenExecutor(), then.action)
        notifier.fireTestFinished(description)
    }

    override fun getDescription(): Description = Description.createSuiteDescription("Then ${then.description}")
}
