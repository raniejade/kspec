package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunNotifier

class JUnitTestExecutor(val notifier: RunNotifier, val contextDescriptions: Map<Context, Description>): ContextVisitor {
    override fun preVisitExampleGroup(context: ExampleGroupContext) {
        safeRun(context) { context, desc ->
            context.before?.invoke()
        }
    }


    override fun postVisitExampleGroup(context: ExampleGroupContext) {
        safeRun(context) { context, desc ->
            context.after?.invoke()
        }
    }

    override fun preVisitExample(context: ExampleContext) {
        safeRun(context) { context, desc ->
            if (context.pending) {
                notifier.fireTestIgnored(desc)
            } else {
                notifier.fireTestStarted(desc)
            }
        }
    }

    override fun onVisitExample(context: ExampleContext) {
        if (!context.pending) {
            safeRun(context) { context, desc ->
                invokeBeforeEach(context.parent)

                // ensures that afterEach is still invoke even if the test fails
                safeRun(context) { context, desc ->
                    context()
                }

                invokeAfterEach(context.parent)
            }
        }
    }

    override fun postVisitExample(context: ExampleContext) {
        if (!context.pending) {
            safeRun(context) { context, desc ->
                notifier.fireTestFinished(contextDescriptions[context])
            }
        }
    }

    private fun invokeBeforeEach(context: ExampleGroupContext) {
        if (context.parent != null) {
            invokeBeforeEach(context.parent)
        }
        context.beforeEach?.invoke()
    }

    private fun invokeAfterEach(context: ExampleGroupContext) {
        context.afterEach?.invoke()
        if (context.parent != null) {
            invokeAfterEach(context.parent)
        }
    }

    private fun <T: Context>safeRun(context: T, action: (T, Description) -> Unit) {
        val desc = contextDescriptions[context];
        try {
            action(context, desc!!)
        } catch (e: Throwable) {
            notifier.fireTestFailure(Failure(desc, e))
        }
    }
}
