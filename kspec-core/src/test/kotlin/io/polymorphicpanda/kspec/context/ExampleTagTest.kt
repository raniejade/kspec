package io.polymorphicpanda.kspec.context

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.tag.Tag
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class ExampleTagTest {
    @Test
    fun testExampleTagCheck() {
        val focus = Tag("focus")
        val test = Tag("test")
        val tags = setOf(focus)
        val example = ExampleContext("example", ExampleGroupContext("group", null), null, tags)

        assertThat(example.contains(focus), equalTo(true))
        assertThat(example.contains(test), equalTo(false))
    }
}
