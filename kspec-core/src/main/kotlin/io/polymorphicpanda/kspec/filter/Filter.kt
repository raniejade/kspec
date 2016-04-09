package io.polymorphicpanda.kspec.filter

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ContextVisitor
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.extension.Extension
import io.polymorphicpanda.kspec.tag.Tag

/**
 * @author Ranie Jade Ramiso
 */
object Filter: Extension {

    override fun configure(config: KSpecConfig, root: ExampleGroupContext) {

        val match = config.filter.match

        if (match.isNotEmpty() && hasMatch(match, root)) {
            config.around { example, chain ->
                if (example.contains(match)) {
                    chain.next(example)
                } else {
                    chain.stop("Not matching include filter")
                }
            }
        }

        val includes = config.filter.includes

        if (includes.isNotEmpty()) {
            config.around { example, chain ->
                if (example.contains(includes)) {
                    chain.next(example)
                } else {
                    chain.stop("Not matching include filter")
                }
            }
        }

        val excludes = config.filter.excludes

        if (excludes.isNotEmpty()) {
            config.around { example, chain ->
                if (!example.contains(excludes)) {
                    chain.next(example)
                } else {
                    chain.stop("Matching exclude filter")
                }
            }
        }

    }

    class StopProcessingException: RuntimeException()

    fun hasMatch(tags: Set<Tag>, root: ExampleGroupContext): Boolean {
        var match = false
        val check = object: ContextVisitor {
            override fun preVisitExampleGroup(context: ExampleGroupContext): Boolean {
                return true
            }

            override fun postVisitExampleGroup(context: ExampleGroupContext) {
            }

            override fun onVisitExample(context: ExampleContext) {
                match = tags.any {
                    context.contains(it)
                }

                if (match) {
                    throw StopProcessingException()
                }
            }
        }

        try {
            root.visit(check)
        } catch (e: StopProcessingException) {
            // do nothing - this is valid
        }

        return match
    }
}
