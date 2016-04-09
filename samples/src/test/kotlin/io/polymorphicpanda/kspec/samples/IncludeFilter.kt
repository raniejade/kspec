package io.polymorphicpanda.kspec.samples

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.junit.JUnitKSpecRunner
import io.polymorphicpanda.kspec.tag.Tag
import org.junit.runner.RunWith

/**
 * @author Ranie Jade Ramiso
 */
@RunWith(JUnitKSpecRunner::class)
class IncludeFilter: KSpec() {
    val tag = Tag("bar")

    override fun configure(config: KSpecConfig) {
        config.filter.include(tag)
    }

    override fun spec() {
        describe("a group") {
            it("some example", tag) {
                assertThat(true, equalTo(true))
            }

            it("i should be ignored") {
                assertThat(true, equalTo(false))
            }
        }
    }
}
