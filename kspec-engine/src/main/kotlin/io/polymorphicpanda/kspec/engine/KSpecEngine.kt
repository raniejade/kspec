package io.polymorphicpanda.kspec.engine

import io.polymorphicpanda.kspec.Configuration
import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.StandardConfiguration
import io.polymorphicpanda.kspec.Utils
import io.polymorphicpanda.kspec.annotation.Configurations
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.engine.discovery.DiscoveryRequest
import io.polymorphicpanda.kspec.engine.discovery.DiscoveryResult
import io.polymorphicpanda.kspec.engine.execution.ExecutionNotifier
import io.polymorphicpanda.kspec.engine.execution.ExecutionRequest
import io.polymorphicpanda.kspec.engine.execution.ExecutionResult
import io.polymorphicpanda.kspec.engine.filter.FilteringVisitor
import io.polymorphicpanda.kspec.engine.filter.MatchingVisitor
import io.polymorphicpanda.kspec.engine.query.Query
import io.polymorphicpanda.kspec.hook.AroundHook
import io.polymorphicpanda.kspec.hook.Chain
import io.polymorphicpanda.kspec.tag.Tag
import java.util.*
import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
class KSpecEngine(val notifier: ExecutionNotifier) {
    fun discover(discoveryRequest: DiscoveryRequest): DiscoveryResult {
        val instances = LinkedHashMap<KSpec, KSpecConfig>()

        discoveryRequest.specs.forEach {
            val instance = Utils.instantiateUsingNoArgConstructor(it)
            val config = discover(instance, discoveryRequest)
            instances.put(instance, config)
        }

        return DiscoveryResult(instances)
    }

    fun execute(discoveryResult: DiscoveryResult) {
        execute(ExecutionRequest(discoveryResult))
    }

    fun execute(executionRequest: ExecutionRequest) {
        notifier.notifyExecutionStarted()
        executionRequest.discoveryResult.instances.forEach {
            execute(it.value, it.key.root)
        }

        notifier.notifyExecutionFinished()
    }

    private fun execute(config: KSpecConfig, context: Context) {
        println(context.description)
        if (context.contains(config.filter.ignore)) {
            notifyContextIgnored(context)
        } else {
            config.before.filter { it.handles(context) }
                .forEach { it.execute(context) }

            val aroundHooks = LinkedList<AroundHook>(
                config.around.filter { it.handles(context) }
            )

            aroundHooks.addLast(AroundHook({ context, chain ->

                when(context) {
                    is Context.Example -> {
                        notifier.notifyExampleStarted(context)

                        try {
                            invokeBeforeEach(context.parent)

                            // ensures that afterEach is still invoke even if the test fails
                            try {
                                context.execute()
                                notifier.notifyExampleFinished(context, ExecutionResult.success(context))
                            } catch (e: Throwable) {
                                notifier.notifyExampleFinished(context, ExecutionResult.failure(context, e))
                            }

                            invokeAfterEach(context.parent)

                        } catch (e: Throwable) {
                            notifier.notifyExampleFinished(context, ExecutionResult.failure(context, e))
                        }
                    }
                    is Context.ExampleGroup -> {
                        try {
                            context.before?.invoke()
                            notifier.notifyExampleGroupStarted(context)

                            context.children.forEach {
                                execute(config, it)
                            }

                            context.after?.invoke()
                            notifier.notifyExampleGroupFinished(context, ExecutionResult.success(context))

                        } catch(e: Throwable) {
                            notifier.notifyExampleGroupFinished(context, ExecutionResult.failure(context, e))
                        }
                    }
                }
            }, emptySet()))

            val exec = Chain(aroundHooks)

            exec.next(context)


            config.after.filter { it.handles(context) }
                .forEach { it.execute(context) }
        }
    }

    private fun notifyContextIgnored(context: Context) {
        when(context) {
            is Context.ExampleGroup -> notifier.notifyExampleGroupIgnored(context)
            is Context.Example -> notifier.notifyExampleIgnored(context)
        }
    }

    private fun applyMatchFilter(root: Context.ExampleGroup, match: Set<Tag>) {
        val predicate: (Context) -> Boolean = {
            it.contains(match)
        }

        val matchingVisitor = MatchingVisitor(predicate)
        root.visit(matchingVisitor)

        if (matchingVisitor.matches) {
            root.visit(FilteringVisitor(predicate))
        }
    }

    private fun applyIncludeFilter(root: Context.ExampleGroup, includes: Set<Tag>) {
        root.visit(FilteringVisitor({
            it.contains(includes)
        }))
    }

    private fun applyExcludeFilter(root: Context.ExampleGroup, excludes: Set<Tag>) {
        root.visit(FilteringVisitor({
            !it.contains(excludes)
        }))
    }

    private fun applyQueryFilter(root: Context.ExampleGroup, query: Query) {
        root.visit(FilteringVisitor({
            query.matches(Query.transform(it))
        }))
    }

    private fun discover(spec: KSpec, discoveryRequest: DiscoveryRequest): KSpecConfig {
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

        if (filter.match.isNotEmpty()) {
            applyMatchFilter(spec.root, filter.match)
        }

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

        return config
    }

    private fun invokeBeforeEach(context: Context.ExampleGroup) {
        if (context.parent != null) {
            invokeBeforeEach(context.parent!!)
        }
        context.beforeEach?.invoke()
    }

    private fun invokeAfterEach(context: Context.ExampleGroup) {
        context.afterEach?.invoke()
        if (context.parent != null) {
            invokeAfterEach(context.parent!!)
        }
    }
}
