package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Root
import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When

/**
 * @author Ranie Jade Ramiso
 */
internal class FeatureCollector: Root {
    internal val roots: MutableList<Parent<Given, Parent<When, Node<Then>>>> = mutableListOf()

    class WhenCollector(val root: Parent<Given, Parent<When, Node<Then>>>) : Given {
        override fun When(description: String, init: When.() -> Unit) {
            val node = Parent<When, Node<Then>>(description, init)
            ThenCollector(node).init()
            root.children.add(node)
        }

    }

    class ThenCollector(val root: Parent<When, Node<Then>>) : When {
        override fun Then(description: String, init: Then.() -> Unit) {
            val node = Node(description, init)
            root.children.add(node)
        }

    }

    override fun Given(description: String, init: Given.() -> Unit) {
        val root = Parent<Given, Parent<When, Node<Then>>>(description, init)
        WhenCollector(root).init()
        roots.add(root)
    }
}
