package io.polymorphicpanda.kspec.runner

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.support.setupSpec
import io.polymorphicpanda.kspec.tag.Tag
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class AroundHookTest {
    @Test
    fun testAroundHookExecutionOrder() {
        val builder = StringBuilder()
        val config = KSpecConfig()

        config.around { example, run, ignore ->
            builder.appendln("begin around")
            run()
            builder.appendln("end around")
        }

        val root = setupSpec {
            describe("group") {

                beforeEach {
                    builder.appendln("beforeEach")
                }
                it("example") {
                    builder.appendln("example")
                }

                it("another example") {
                    builder.appendln("another example")
                }

                afterEach {
                    builder.appendln("afterEach")
                }
            }
        }

        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        begin around
        beforeEach
        example
        afterEach
        end around
        begin around
        beforeEach
        another example
        afterEach
        end around
        """.trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testAroundHookIgnore() {
        val builder = StringBuilder()
        val config = KSpecConfig()

        config.around { example, run, ignore ->
            builder.appendln("begin around")

            val tag = example["ignored"]
            if (tag != null) {
                ignore("no reason given")
            } else {
                run()
            }
            builder.appendln("end around")

        }

        val root = setupSpec {
            describe("group") {
                it("example") {
                    builder.appendln("example")
                }

                it("ignored example", Tag("ignored")) {
                    builder.appendln("ignored example")
                }
            }
        }

        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        begin around
        example
        end around
        begin around
        end around
        """.trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
}
