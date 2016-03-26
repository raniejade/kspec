package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import org.junit.runner.Description
import java.util.*

class JUnitTestDescriber: ContextVisitor {
    val contextDescriptions = LinkedHashMap<Context, Description>()

    private fun className(context: Context?): String {
        if (context == null) {
            return ""
        }

        return when(context) {
            is ExampleContext -> "${className(context.parent)}.${context.description}"
            is ExampleGroupContext -> {
                val parent = className(context.parent)

                if (parent.isEmpty()) {
                    return context.description
                }

                "$parent.${context.description}"
            }
            else -> ""
        }

    }

    override fun preVisitExampleGroup(context: ExampleGroupContext) {
        contextDescriptions.put(context, Description.createSuiteDescription(context.description))
    }

    override fun onVisitExampleGroup(context: ExampleGroupContext) {
        super.onVisitExampleGroup(context)
    }

    override fun postVisitExampleGroup(context: ExampleGroupContext) {
        val current = contextDescriptions[context]

        if (context.parent != null) {
            val parent = contextDescriptions[context.parent]
            parent!!.addChild(current)
        }
    }

    override fun preVisitExample(context: ExampleContext) {
        contextDescriptions.put(
                context,
                Description.createTestDescription(className(context.parent), context.description)
        )
    }

    override fun onVisitExample(context: ExampleContext) {
        super.onVisitExample(context)
    }

    override fun postVisitExample(context: ExampleContext) {
        val current = contextDescriptions[context]
        val parent = contextDescriptions[context.parent]
        parent!!.addChild(current)
    }
}

