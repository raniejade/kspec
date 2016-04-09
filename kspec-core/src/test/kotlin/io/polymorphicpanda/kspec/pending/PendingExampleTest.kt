package io.polymorphicpanda.kspec.pending

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.runner.KSpecRunner
import io.polymorphicpanda.kspec.runner.RunNotifier
import io.polymorphicpanda.kspec.support.setupSpec
import io.polymorphicpanda.kspec.xit
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class PendingExampleTest {
    @Test
    fun testSingle() {
        val builder = StringBuilder()

        val root = setupSpec {
            describe("group") {
                xit("pending example") {
                    builder.appendln("pending example")
                }

                it("not a pending example") {
                    builder.appendln("not a pending example")
                }
            }
        }

        val config = KSpecConfig()

        val notifier = RunNotifier()
        val runner = KSpecRunner(root, config)

        runner.run(notifier)

        val expected = """
        not a pending example
        """.trimIndent()

        assertThat(builder.trimEnd().toString(), equalTo(expected))
    }

}
