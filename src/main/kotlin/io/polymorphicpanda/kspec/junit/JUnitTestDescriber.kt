package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import org.junit.runner.Description
import java.util.*

class JUnitTestDescriber: ContextVisitor {
    val contextDescriptions = LinkedHashMap<Context, Description>()

    override fun pre(context: Context) {
        contextDescriptions.put(context, Description.createSuiteDescription(context.description))
    }

    override fun post(context: Context) {
        val current = contextDescriptions[context]

        if (context.parent != null) {
            val parent = contextDescriptions[context.parent!!]
            parent!!.addChild(current)
        }
    }
}

