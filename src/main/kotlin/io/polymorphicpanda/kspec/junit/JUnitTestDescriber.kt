package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import org.junit.runner.Description
import java.util.*

class JUnitTestDescriber: ContextVisitor {
    val contextDescriptions = LinkedHashMap<Context, Description>()

    override fun pre(context: Context) {
        if (!context.example) {
            contextDescriptions.put(context, Description.createSuiteDescription(context.description))
        } else {
            contextDescriptions.put(
                    context,
                    Description.createTestDescription(className(context.parent), context.description)
            )
        }
    }

    override fun post(context: Context) {
        val current = contextDescriptions[context]

        if (context.parent != null) {
            val parent = contextDescriptions[context.parent]
            parent!!.addChild(current)
        }
    }

    private fun className(context: Context?): String {
        if (context == null) {
            return ""
        }
        val parent = className(context.parent)

        if (parent.isEmpty()) {
            return context.description
        }

        return "$parent.${context.description}"
    }
}

