package io.polymorphicpanda.kspec

import com.natpryce.hamkrest.anything
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.nothing
import com.natpryce.hamkrest.present
import com.natpryce.hamkrest.throws
import org.junit.Test
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class UtilsTest {
    @Target(AnnotationTarget.CLASS)
    annotation class Foo

    @Test
    fun testFindAnnotationNull() {
        class Stub
        assertThat(Utils.findAnnotation(Stub::class, Foo::class), !present(nothing))
    }

    @Test
    fun testFindAnnotation() {
        @Foo
        class Stub

        assertThat(Utils.findAnnotation(Stub::class, Foo::class), present(anything))
    }

    @Test
    fun testInstantiateUsingNoArgConstructor() {
        class Stub

        assertThat({
            Utils.instantiateUsingNoArgConstructor(Stub::class)
        }, !throws<Throwable>())
    }

    @Test
    fun testInstantiateUsingNoArgConstructorFailure() {
        class Stub(val foo: Int)

        assertThat({
            Utils.instantiateUsingNoArgConstructor(Stub::class)
        }, throws<NoSuchElementException>())
    }
}
