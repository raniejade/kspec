package io.polymorphicpanda.speck.runner

internal open class Node<T>(val description: String, val action: T.() -> Unit)

internal class Parent<T, C: Node<*>>(description: String, action: T.() -> Unit): Node<T>(description, action) {
    val children: MutableList<C> = mutableListOf()
}

