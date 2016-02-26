package io.polymorphicpanda.speck.runner

import org.junit.runner.Description

/**
 * @author Ranie Jade Ramiso
 */
internal sealed class Node<T>(val description: Description, val action: T.() -> Unit) {
    class ParentNode<T, C: Node<*>>(description: Description, action: T.() -> Unit): Node<T>(description, action) {
        val children: MutableList<C> = mutableListOf()
    }

    class LeafNode<T>(description: Description, action: T.() -> Unit): Node<T>(description, action)
}
