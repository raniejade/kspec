package io.polymorphicpanda.kspec.samples.configuration

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.annotation.Configurations
import io.polymorphicpanda.kspec.context
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.junit.JUnitKSpecRunner
import org.junit.runner.RunWith

/**
 * @author Ranie Jade Ramiso
 */
@RunWith(JUnitKSpecRunner::class)
@Configurations(SharedConfiguration::class)
class SharedConfigurationExample: KSpec() {
    override fun spec() {
        describe("foo") {
            context("bar", SharedConfiguration.TAG) {
                it("sample") {
                    // pass
                }
            }

            it("nope") {
                assertThat(true, equalTo(false))
            }
        }
    }
}
