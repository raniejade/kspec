package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When
import org.junit.runner.Description
import org.junit.runner.notification.RunNotifier
import org.junit.runners.ParentRunner

/**
 * @author Ranie Jade Ramiso
 */
internal class GivenRunner(testClass: Class<*>,
                           val node: Parent<Given, Parent<When, Node<Then>>>): ParentRunner<WhenRunner>(testClass) {
    override fun describeChild(child: WhenRunner): Description = child.description

    override fun runChild(child: WhenRunner, notifier: RunNotifier) {
        child.run(notifier)
    }

    override fun getChildren(): List<WhenRunner> {
        return node.children.map { WhenRunner(testClass.javaClass, it) }
    }

    override fun getName(): String = "Given ${node.description}"
}
