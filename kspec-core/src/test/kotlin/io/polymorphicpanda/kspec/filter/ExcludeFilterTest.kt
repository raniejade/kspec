package io.polymorphicpanda.kspec.filter

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.runner.KSpecRunner
import io.polymorphicpanda.kspec.runner.RunNotifier
import io.polymorphicpanda.kspec.support.setupSpec
import io.polymorphicpanda.kspec.tag.Tag
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class ExcludeFilterTest {
    @Test
    fun testSingle() {
        val builder = StringBuilder()

        val tag1 = Tag("tag1")

        val root = setupSpec {
            describe("group") {
                it("example", tag1) {
                    builder.appendln("example")
                }

                it("another example") {
                    builder.appendln("another example")
                }
            }
        }

        val config = KSpecConfig()

        config.filter.exclude(tag1)

        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        another example
        """.trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

    @Test
    fun testMultiple() {
        val builder = StringBuilder()

        val tag1 = Tag("tag1")
        val tag2 = Tag("tag2")

        val root = setupSpec {
            describe("group") {
                it("example", tag1) {
                    builder.appendln("example")
                }

                it("another example", tag2) {
                    builder.appendln("another example")
                }

                it("yet another example") {
                    builder.appendln("yet another example")
                }
            }
        }

        val config = KSpecConfig()

        config.filter.exclude(tag1, tag2)

        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        yet another example
        """.trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
}