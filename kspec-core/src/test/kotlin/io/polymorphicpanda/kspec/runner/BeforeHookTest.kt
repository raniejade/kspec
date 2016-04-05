package io.polymorphicpanda.kspec.runner

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.support.setupSpec
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class BeforeHookTest {
    @Test
    fun testBeforeHookExecutionOrder() {
        val builder = StringBuilder()
        val config = KSpecConfig()

        config.before {
            builder.appendln("before hook")
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
            }
        }

        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        before hook
        beforeEach
        example
        before hook
        beforeEach
        another example""".trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
}
