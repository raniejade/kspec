package io.polymorphicpanda.kspec.runner

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ContextVisitor
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.extension.CoreExtensions
import io.polymorphicpanda.kspec.hook.Chain

/**
 * @author Ranie Jade Ramiso
 */
class KSpecRunner(val root: ExampleGroupContext, val config: KSpecConfig = KSpecConfig()) {
    fun run(notifier: RunNotifier) {
        val clone = config.clone()

        CoreExtensions.configure(clone, root)

        root.visit(Runner(notifier, clone))
    }

    internal class Runner(val notifier: RunNotifier, val config: KSpecConfig): ContextVisitor {

        init {
            config.around { example, chain ->
                notifier.notifyExampleStarted(example)

                invokeBeforeEach(example.parent)

                // ensures that afterEach is still invoke even if the test fails
                safeRun(example) { context ->
                    context()
                }

                invokeAfterEach(example.parent)

                notifier.notifyExampleFinished(example)
            }
        }

        override fun preVisitExampleGroup(context: ExampleGroupContext): Boolean {
            return safeRun(context) { context ->
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

        override fun onVisitExample(context: ExampleContext) {
            safeRun(context) { context ->
                config.before.filter { it.handles(context) }
                        .forEach { it.execute(context) }

                val aroundHooks = config.around.filter { it.handles(context) }
                val chain = object: Chain(aroundHooks) {
                    override fun stop(reason: String) {
                        notifier.notifyExampleIgnored(context)
                    }
                }

                chain.next(context)

                config.after.filter { it.handles(context) }
                        .forEach { it.execute(context) }
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

        private fun <T: Context> safeRun(context: T, action: (T) -> Unit): Boolean {
            try {
                action(context)
                return true
            } catch (e: Throwable) {
                when(context) {
                    is ExampleContext -> notifier.notifyExampleFailure(context, e)
                    is ExampleGroupContext -> notifier.notifyExampleGroupFailure(context, e)
                }
            }
            return false
        }
    }
}
