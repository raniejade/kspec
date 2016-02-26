package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When
import org.junit.runner.Description

/**
 * @author Ranie Jade Ramiso
 */
internal class FeatureCollector: DslWalker {
    class WhenCollector(val root: Node.ParentNode<Given, Node.ParentNode<When, Node.LeafNode<Then>>>) : Given {
        override fun When(description: String, init: When.() -> Unit) {
            val desc = Description.createSuiteDescription("When $description")
            val node = Node.ParentNode<When, Node.LeafNode<Then>>(desc, init)
            ThenCollector(node).init()
            root.children.add(node)
        }

    }

    class ThenCollector(val root: Node.ParentNode<When, Node.LeafNode<Then>>) : When {
        override fun Then(description: String, init: Then.() -> Unit) {
            val desc = Description.createSuiteDescription("Then $description")
            val node = Node.LeafNode(desc, init)
            root.children.add(node)
        }

    }

    override fun walk(description: String, init: Given.() -> Unit): Feature {
        val desc = Description.createSuiteDescription("Given $description")
        val root = Node.ParentNode<Given, Node.ParentNode<When, Node.LeafNode<Then>>>(desc, init)
        WhenCollector(root).init()
        return Feature(root)
    }
}
