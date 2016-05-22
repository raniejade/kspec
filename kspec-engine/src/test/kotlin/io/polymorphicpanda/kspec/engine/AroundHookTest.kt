package io.polymorphicpanda.kspec.engine

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.engine.discovery.DiscoveryRequest
import io.polymorphicpanda.kspec.engine.execution.ExecutionNotifier
import io.polymorphicpanda.kspec.engine.execution.ExecutionRequest
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.tag.SimpleTag
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class AroundHookTest {
    object Tag1: SimpleTag()

    @Test
    fun testMatchTag() {
        val builder = StringBuilder()
        val config = KSpecConfig()
        val notifier = ExecutionNotifier()
        val engine = KSpecEngine(notifier)

        config.around(Tag1::class) { context, chain ->
            builder.appendln(context.description)
            chain.next(context)
        }

        class TestSpec: KSpec() {
            override fun spec() {
                describe("group") {
                    context("context", Tag1) {
                        it("example") { }
                    }

                    it("another example", Tag1) { }
                }
            }
        }

        val result = engine.discover(DiscoveryRequest(listOf(TestSpec::class), config))

        val expected = """
        context: context
        it: example
        it: another example
        """.trimIndent()

        engine.execute(ExecutionRequest(result))

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testMatchAll() {
        val builder = StringBuilder()
        val config = KSpecConfig()
        val notifier = ExecutionNotifier()
        val engine = KSpecEngine(notifier)

        config.around { context, chain ->
            builder.appendln(context.description)
            chain.next(context)
        }

        class TestSpec: KSpec() {
            override fun spec() {
                describe("group") {
                    context("context", Tag1) {
                        it("example") { }
                    }

                    it("another example", Tag1) { }
                }
            }
        }

        val result = engine.discover(DiscoveryRequest(listOf(TestSpec::class), config))

        val expected = """
        ${TestSpec::class.java.name}
        describe: group
        context: context
        it: example
        it: another example
        """.trimIndent()

        engine.execute(ExecutionRequest(result))

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
}
