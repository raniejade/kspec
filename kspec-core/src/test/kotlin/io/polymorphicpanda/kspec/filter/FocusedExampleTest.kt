package io.polymorphicpanda.kspec.filter

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.context
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.fit
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.runner.KSpecRunner
import io.polymorphicpanda.kspec.runner.RunNotifier
import io.polymorphicpanda.kspec.support.setupSpec
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class FocusedExampleTest {
    @Test
    fun testMatch() {
        val builder = StringBuilder()

        val root = setupSpec {
            describe("group") {
                fit("focused example") {
                    builder.appendln("focused example")
                }

                it("example") {
                    builder.appendln("example")
                }

                context("bar") {
                    fit("another focused example") {
                        builder.appendln("another focused example")
                    }
                }
            }
        }

        val config = KSpecConfig()
        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        focused example
        another focused example
        """.trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testNoMatch() {
        val builder = StringBuilder()

        val root = setupSpec {
            describe("group") {
                it("example") {
                    builder.appendln("example")
                }

                it("another example") {
                    builder.appendln("another example")
                }

                context("bar") {
                    it("yet another example") {
                        builder.appendln("yet another example")
                    }
                }
            }
        }

        val config = KSpecConfig()
        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        example
        another example
        yet another example
        """.trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
}
