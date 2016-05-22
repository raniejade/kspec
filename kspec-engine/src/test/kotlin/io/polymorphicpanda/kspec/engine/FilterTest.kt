package io.polymorphicpanda.kspec.engine

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context
import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.engine.discovery.DiscoveryRequest
import io.polymorphicpanda.kspec.engine.execution.ExecutionListenerAdapter
import io.polymorphicpanda.kspec.engine.execution.ExecutionNotifier
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.tag.SimpleTag
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class FilterTest {
    object Tag1: SimpleTag()
    object Tag2: SimpleTag()
    object Tag3: SimpleTag()


    @Test
    fun testIncludeExample() {
        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class IncludeSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.include(Tag1::class)
            }

            override fun spec() {
                describe("group") {
                    it("example") { }
                    it("included example", Tag1) { }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(IncludeSpec::class), KSpecConfig(), null))

        val expected = """
        it: included example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testIncludeExampleGroup() {
        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class IncludeSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.include(Tag1::class)
            }

            override fun spec() {
                describe("group") {
                    it("example") { }

                    context("context", Tag1) {
                        it("included example") { }
                    }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(IncludeSpec::class), KSpecConfig(), null))

        val expected = """
        it: included example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testExcludeExample() {
        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class ExcludeSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.exclude(Tag1::class)
            }

            override fun spec() {
                describe("group") {
                    it("example") { }
                    it("included example", Tag1) { }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(ExcludeSpec::class), KSpecConfig(), null))

        val expected = """
        it: example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testExcludeExampleGroup() {
        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class ExcludeSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.exclude(Tag1::class)
            }

            override fun spec() {
                describe("group") {
                    it("example") { }

                    context("context", Tag1) {
                        it("included example") { }
                    }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(ExcludeSpec::class), KSpecConfig(), null))

        val expected = """
        it: example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testMatchExample() {
        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class MatchSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.matching(Tag1::class)
            }

            override fun spec() {
                describe("group") {
                    it("example") { }
                    it("included example", Tag1) { }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(MatchSpec::class), KSpecConfig(), null))

        val expected = """
        it: included example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testMatchExampleGroup() {
        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class MatchSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.matching(Tag1::class)
            }

            override fun spec() {
                describe("group") {
                    it("example") { }

                    context("context", Tag1) {
                        it("included example") { }
                    }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(MatchSpec::class), KSpecConfig(), null))

        val expected = """
        it: included example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
    @Test
    fun testNoMatch() {
        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class MatchSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.matching(Tag1::class)
            }

            override fun spec() {
                describe("group") {
                    it("example") { }

                    context("context") {
                        it("another example") { }
                    }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(MatchSpec::class), KSpecConfig()))

        val expected = """
        it: example
        it: another example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testFilterPrecedence() {
        /**
         * The order filters are applied is:
         * 1. Match
         * 2. Include
         * 3. Exclude
         */

        val builder = StringBuilder()
        val notifier = ExecutionNotifier()

        notifier.addListener(object: ExecutionListenerAdapter() {
            override fun exampleStarted(example: Context.Example) {
                builder.appendln(example.description)
            }
        })

        val engine = KSpecEngine(notifier)

        class FilterSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                config.filter.matching(Tag1::class)
                config.filter.include(Tag2::class)
                config.filter.exclude(Tag3::class)
            }

            override fun spec() {
                describe("group") {
                    it("first included example", Tag1) {
                        /**
                         * not executed, has match filter but not include filter tag
                         */
                    }

                    it("unmatched example", Tag2) { }

                    context("matched context", Tag1) {
                        it("excluded example", Tag3) { }
                        it ("included example", Tag2) {
                            /**
                             * Executed as parent has match filter and example has include tag
                             */
                        }

                        describe("included group", Tag2) {
                            it ("some example") {
                                /**
                                 * Executed as parent has include filter tag
                                 */
                            }
                        }
                    }
                }
            }

        }

        val result = engine.discover(DiscoveryRequest(listOf(FilterSpec::class), KSpecConfig()))

        val expected = """
        it: included example
        it: some example
        """.trimIndent()

        engine.execute(result)

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
}
