package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When
import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runners.ParentRunner

/**
 * @author Ranie Jade Ramiso
 */
internal class WhenRunner(testClass: Class<*>,
                          val node: Parent<When, Node<Then>>): ParentRunner<ThenRunner>(testClass) {
    override fun describeChild(child: ThenRunner): Description = child.description

    override fun runChild(child: ThenRunner, notifier: RunNotifier) {
        child.run(notifier)
    }

    override fun getChildren(): List<ThenRunner> {
        return node.children.map { ThenRunner(it) }
    }

    override fun getName(): String = "When ${node.description}"
}
