package io.polymorphicpanda.kspec.engine

import io.polymorphicpanda.kspec.Configuration
import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.StandardConfiguration
import io.polymorphicpanda.kspec.Utils
import io.polymorphicpanda.kspec.annotation.Configurations
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.engine.discovery.DiscoveryRequest
import io.polymorphicpanda.kspec.engine.discovery.DiscoveryResult
import io.polymorphicpanda.kspec.engine.execution.*
import io.polymorphicpanda.kspec.engine.filter.FilteringVisitor
import io.polymorphicpanda.kspec.engine.query.Query
import io.polymorphicpanda.kspec.tag.Tag
import java.util.*
import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
class KSpecEngine(val notifier: ExecutionNotifier) {
    fun discover(discoveryRequest: DiscoveryRequest): DiscoveryResult {
        val instances = LinkedList<KSpec>()

        discoveryRequest.specs.forEach {
            val instance = Utils.instantiateUsingNoArgConstructor(it)
            discover(instance, discoveryRequest)
            instances.add(instance)
        }

        return DiscoveryResult(instances)
    }

    fun execute(discoveryResult: DiscoveryResult) {
        execute(ExecutionRequest(KSpecConfig(), discoveryResult))
    }

    fun execute(executionRequest: ExecutionRequest) {
        notifier.notifyExecutionStarted()
        executionRequest.discoveryResult.instances.forEach { spec ->
            // apply global configuration
            val config = KSpecConfig()
            config.copy(executionRequest.config)

            // apply shared configurations
            val annotation = Utils.findAnnotation(spec.javaClass.kotlin, Configurations::class)
            if (annotation != null) {
                val configurations = annotation.configurations
                configurations.forEach { it: KClass<out Configuration> ->
                    val configuration = Utils.instantiateUsingNoArgConstructor(it)
                    configuration.apply(config)
                }
            }

            // apply spec configuration
            spec.configure(config)

            // built-in configurations
            StandardConfiguration.apply(config)

            val root = spec.root

            val filter = Filter(root, config.filter)
            val chain = ExecutorChain().apply {
                + MatchExecutor(filter, notifier)
                + HookExecutor(config, notifier)
                + ActualExecutor(config)
            }


            // start the execution chain
            chain.next(root)
        }

        notifier.notifyExecutionFinished()
    }

    inner class ActualExecutor(val config: KSpecConfig): Executor {
        override fun execute(context: Context, chain: ExecutorChain) {
            if (context.contains(config.filter.ignore)) {
                notifyContextIgnored(context)
            } else {
                when(context) {
                    is ExampleContext -> {
                        notifier.notifyExampleStarted(context)

                        try {
                            invokeBeforeEach(context.parent)

                            // ensures that afterEach is still invoke even if the test fails
                            try {
                                context()
                                notifier.notifyExampleFinished(context, ExecutionResult.success(context))
                            } catch (e: Throwable) {
                                notifier.notifyExampleFinished(context, ExecutionResult.failure(context, e))
                            }

                            invokeAfterEach(context.parent)

                        } catch (e: Throwable) {
                            notifier.notifyExampleFinished(context, ExecutionResult.failure(context, e))
                        }
                    }
                    is ExampleGroupContext -> {
                        try {
                            context.before?.invoke()
                            notifier.notifyExampleGroupStarted(context)

                            context.children.forEach {
                                chain.reset()
                                chain.next(it)
                            }

                            context.after?.invoke()
                            notifier.notifyExampleGroupFinished(context, ExecutionResult.success(context))

                        } catch(e: Throwable) {
                            notifier.notifyExampleGroupFinished(context, ExecutionResult.failure(context, e))
                        }
                    }
                }
            }
        }

        fun notifyContextIgnored(context: Context) {
            when(context) {
                is ExampleGroupContext -> notifier.notifyExampleGroupIgnored(context)
                is ExampleContext -> notifier.notifyExampleIgnored(context)
            }
        }

    }

    private fun applyIncludeFilter(root: ExampleGroupContext, includes: Set<Tag>) {
        root.visit(FilteringVisitor({
            it.contains(includes)
        }))
    }

    private fun applyExcludeFilter(root: ExampleGroupContext, excludes: Set<Tag>) {
        root.visit(FilteringVisitor({
            !it.contains(excludes)
        }))
    }

    private fun applyQueryFilter(root: ExampleGroupContext, query: Query) {
        root.visit(FilteringVisitor({
            query.matches(Query.transform(it))
        }))
    }

    private fun discover(spec: KSpec, discoveryRequest: DiscoveryRequest) {
        spec.spec()

        // apply global configuration
        val config = KSpecConfig()
        config.copy(discoveryRequest.config)

        // apply shared configurations
        val annotation = Utils.findAnnotation(spec.javaClass.kotlin, Configurations::class)
        if (annotation != null) {
            val configurations = annotation.configurations
            configurations.forEach { it: KClass<out Configuration> ->
                val configuration = Utils.instantiateUsingNoArgConstructor(it)
                configuration.apply(config)
            }
        }

        // apply spec configuration
        spec.configure(config)

        // built-in configurations
        StandardConfiguration.apply(config)

        val filter = config.filter

        if (filter.includes.isNotEmpty()) {
            applyIncludeFilter(spec.root, filter.includes)
        }

        if (filter.excludes.isNotEmpty()) {
            applyExcludeFilter(spec.root, filter.excludes)
        }

        if (discoveryRequest.query != null) {
            applyQueryFilter(spec.root, discoveryRequest.query)
        }

        spec.lock()
    }

    private fun invokeBeforeEach(context: ExampleGroupContext) {
        if (context.parent != null) {
            invokeBeforeEach(context.parent!!)
        }
        context.beforeEach?.invoke()
    }

    private fun invokeAfterEach(context: ExampleGroupContext) {
        context.afterEach?.invoke()
        if (context.parent != null) {
            invokeAfterEach(context.parent!!)
        }
    }
}
