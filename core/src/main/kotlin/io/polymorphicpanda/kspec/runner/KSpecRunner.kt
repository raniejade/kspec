package io.polymorphicpanda.kspec.runner

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext

/**
 * @author Ranie Jade Ramiso
 */
class KSpecRunner(val root: ExampleGroupContext) {
    fun run(notifier: RunNotifier) {
        root.visit(Runner(notifier))
    }

    class Runner(val notifier: RunNotifier): ContextVisitor {
        override fun preVisitExampleGroup(context: ExampleGroupContext) {
            safeRun(context) { context ->
                notifier.notifyExampleGroupStarted(context)
                context.before?.invoke()
            }
        }


        override fun postVisitExampleGroup(context: ExampleGroupContext) {
            safeRun(context) { context ->
                context.after?.invoke()
                notifier.notifyExampleGroupFinished(context)
            }
        }

        override fun preVisitExample(context: ExampleContext) {
            safeRun(context) { context ->
                if (context.pending) {
                    notifier.notifyExampleIgnored(context)
                } else {
                    notifier.notifyExampleStarted(context)
                }
            }
        }

        override fun onVisitExample(context: ExampleContext) {
            if (!context.pending) {
                safeRun(context) { context ->
                    invokeBeforeEach(context.parent)

                    // ensures that afterEach is still invoke even if the test fails
                    safeRun(context) { context ->
                        context()
                    }

                    invokeAfterEach(context.parent)
                }
            }
        }

        override fun postVisitExample(context: ExampleContext) {
            if (!context.pending) {
                safeRun(context) { context ->
                    notifier.notifyExampleFinished(context)
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

        private fun <T: Context> safeRun(context: T, action: (T) -> Unit) {
            try {
                action(context)
            } catch (e: Throwable) {
                when(context) {
                    is ExampleContext -> notifier.notifyExampleFailure(context, e)
                    is ExampleGroupContext -> notifier.notifyExampleGroupFailure(context, e)
                }
            }
        }
    }
}
