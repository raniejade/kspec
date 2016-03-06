package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunNotifier

class JUnitTestExecutor(val notifier: RunNotifier, val contextDescriptions: Map<Context, Description>): ContextVisitor {
    override fun pre(context: Context) {
        safeRun(context) { context, desc ->
            if (context.terminal) {
                notifier.fireTestStarted(desc)
            }
            context.before?.invoke()
        }
    }

    override fun on(context: Context) {
        safeRun(context) { context, desc ->
            if (context.terminal) {
                invokeBeforeEach(context)
                context()
                invokeAfterEach(context)
            }
        }
    }

    override fun post(context: Context) {
        safeRun(context) { context, desc ->
            context.after?.invoke()
            if (context.terminal) {
                notifier.fireTestFinished(contextDescriptions[context])
            }
        }
    }

    private fun invokeBeforeEach(context: Context) {
        if (context.parent != null) {
            invokeBeforeEach(context.parent!!)
        }
        context.beforeEach?.invoke()
    }

    private fun invokeAfterEach(context: Context) {
        context.afterEach?.invoke()
        if (context.parent != null) {
            invokeAfterEach(context.parent!!)
        }
    }

    private fun safeRun(context: Context, action: (Context, Description) -> Unit) {
        val desc = contextDescriptions[context];
        try {
            action(context, desc!!)
            if (context.failure != null) {
                notifier.fireTestFailure(Failure(desc, context.failure))
            }
        } catch (e: Throwable) {
            notifier.fireTestFailure(Failure(desc, e))
        }
    }
}
