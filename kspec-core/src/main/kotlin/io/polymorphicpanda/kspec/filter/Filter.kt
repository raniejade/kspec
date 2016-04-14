package io.polymorphicpanda.kspec.filter

import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context.ContextVisitResult
import io.polymorphicpanda.kspec.context.ContextVisitor
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.extension.Extension
import io.polymorphicpanda.kspec.tag.Ignored
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
                    val ignored = example.tags.filterIsInstance(Ignored::class.java).firstOrNull()
                    if (ignored != null) {
                        chain.stop(ignored.reason)
                    } else {
                        chain.stop("Matching exclude filter")
                    }
                }
            }
        }

    }

    fun hasMatch(tags: Set<Tag>, root: ExampleGroupContext): Boolean {
        val check = object: ContextVisitor {
            var match = false
            override fun preVisitExampleGroup(context: ExampleGroupContext): ContextVisitResult {
                return ContextVisitResult.CONTINUE
            }

            override fun postVisitExampleGroup(context: ExampleGroupContext) = ContextVisitResult.CONTINUE

            override fun onVisitExample(context: ExampleContext): ContextVisitResult {
                match = tags.any {
                    context.contains(it)
                }

                if (match) {
                    return ContextVisitResult.TERMINATE
                }

                return ContextVisitResult.CONTINUE
            }
        }

        root.visit(check)

        return check.match
    }
}
