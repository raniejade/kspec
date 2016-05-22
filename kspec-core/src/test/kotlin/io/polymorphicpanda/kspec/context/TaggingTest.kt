package io.polymorphicpanda.kspec.context

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.present
import io.polymorphicpanda.kspec.Focus
import io.polymorphicpanda.kspec.focus
import io.polymorphicpanda.kspec.tag.SimpleTag
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class TaggingTest {
    val focus = focus()
    object TestTag: SimpleTag()

    @Test
    fun testTagExample() {
        val tags = setOf(focus)
        val example = Context.Example("example", Context.ExampleGroup("group", null), null, tags)

        assertThat(example.contains(Focus::class), equalTo(true))
        assertThat(example.contains(TestTag::class), equalTo(false))
    }

    @Test
    fun testTagGroup() {
        val parent = Context.ExampleGroup("group", null, setOf(focus))
        val another = Context.ExampleGroup("another", parent)
        val example = Context.Example("example", parent, null, setOf(TestTag))

        assertThat(parent.contains(Focus::class), equalTo(true))
        assertThat(another.contains(Focus::class), equalTo(true))
        assertThat(example.contains(Focus::class), equalTo(true))
        assertThat(example.contains(TestTag::class), equalTo(true))
    }

    @Test
    fun testGetTagExample() {
        val tags = setOf(focus)
        val example = Context.Example("example", Context.ExampleGroup("group", null), null, tags)

        assertThat(example.get<Focus>(), present(equalTo(focus)))
    }

    @Test
    fun testGetTagGroup() {
        val parent = Context.ExampleGroup("group", null, setOf(focus))
        val another = Context.ExampleGroup("another", parent)
        val example = Context.Example("example", parent, null, setOf(TestTag))

        assertThat(parent.contains(Focus::class), equalTo(true))
        assertThat(another.contains(Focus::class), equalTo(true))
        assertThat(example.contains(Focus::class), equalTo(true))
        assertThat(example.contains(TestTag::class), equalTo(true))

        assertThat(parent.get<Focus>(), present(equalTo(focus)))
        assertThat(another.get<Focus>(), present(equalTo(focus)))
        assertThat(example.get<Focus>(), present(equalTo(focus)))
        assertThat(example.get<TestTag>(), present(equalTo(TestTag)))
    }
}
