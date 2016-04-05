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
class AfterHookTest {
    @Test
    fun testAfterHookExecutionOrder() {
        val builder = StringBuilder()
        val config = KSpecConfig()

        config.after {
            builder.appendln("after hook")
        }

        val root = setupSpec {
            describe("group") {

                afterEach {
                    builder.appendln("afterEach")
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
        example
        afterEach
        after hook
        another example
        afterEach
        after hook""".trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }
}
