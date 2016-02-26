package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When

/**
 * @author Ranie Jade Ramiso
 */
internal data class Feature(val root: Node.ParentNode<Given, Node.ParentNode<When, Node.LeafNode<Then>>>)
