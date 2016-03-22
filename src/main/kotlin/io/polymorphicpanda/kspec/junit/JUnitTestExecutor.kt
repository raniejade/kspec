package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.context.GroupContext
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunNotifier

class JUnitTestExecutor(val notifier: RunNotifier, val contextDescriptions: Map<Context, Description>): ContextVisitor {
    override fun preVisitGroup(context: GroupContext) {
        safeRun(context) { context, desc ->
            context.before?.invoke()
        }
    }


    override fun postVisitGroup(context: GroupContext) {
        safeRun(context) { context, desc ->
            context.after?.invoke()
        }
    }

    override fun preVisitExampleGroup(context: ExampleGroupContext) {
        safeRun(context) { context, desc ->
            notifier.fireTestStarted(desc)
        }
    }

    override fun onVisitExampleGroup(context: ExampleGroupContext) {
        safeRun(context) { context, desc ->
            invokeBeforeEach(context.parent)

            // ensures that afterEach is still invoke even if the test fails
            safeRun(context) { context, desc ->
                context()
            }

            invokeAfterEach(context.parent)
        }
    }

    override fun postVisitExampleGroup(context: ExampleGroupContext) {
        safeRun(context) { context, desc ->
            notifier.fireTestFinished(contextDescriptions[context])
        }
    }

    private fun invokeBeforeEach(context: GroupContext) {
        if (context.parent != null) {
            invokeBeforeEach(context.parent)
        }
        context.beforeEach?.invoke()
    }

    private fun invokeAfterEach(context: GroupContext) {
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
