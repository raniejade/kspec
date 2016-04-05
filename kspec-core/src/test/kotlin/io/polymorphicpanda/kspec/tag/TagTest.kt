package io.polymorphicpanda.kspec.tag

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class TagTest {
    @Test(expected = IllegalArgumentException::class)
    fun testTagNameCantBeEmpty() {
        Tag("")
    }

    @Test
    fun testTagEquality() {
        assertThat(Tag("focus"), equalTo(Tag("focus")))
        assertThat(Tag("focus"), equalTo(Tag("Focus")))
        assertThat(Tag("focus"), equalTo(Tag("FOCUS")))
    }

    @Test
    fun testTagFetchData() {
        val tag = Tag("tag", mapOf("value" to true))

        assertThat(tag["value"], equalTo(true))
    }

    @Test(expected = ClassCastException::class)
    fun testTagFetchDataInvalidType() {
        val tag = Tag("tag", mapOf("value" to listOf<String>()))
        assertThat(tag["value"], equalTo(true))
    }
}
