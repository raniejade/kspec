package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Given

/**
 * @author Ranie Jade Ramiso
 */
internal interface DslWalker {
    fun walk(description: String, init: Given.() -> Unit): Feature
}
