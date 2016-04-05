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
        tag("") {}
    }

    @Test
    fun testTagEquality() {
        assertThat(tag("focus") {}, equalTo(tag("focus") {}))
        assertThat(tag("focus") {}, equalTo(tag("Focus") {}))
        assertThat(tag("focus") {}, equalTo(tag("FOCUS") {}))
    }

    @Test
    fun testTagFetchData() {
        val tag = tag("tag") {
            put("value", true)
        }

        assertThat(tag["value"], equalTo(true))
    }

    @Test(expected = ClassCastException::class)
    fun testTagFetchDataInvalidType() {
        val tag = tag("tag") {
            put("value", listOf<String>())
        }

        assertThat(tag["value"], equalTo(true))
    }
}
