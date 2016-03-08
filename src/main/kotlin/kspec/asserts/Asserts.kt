package kspec.asserts

import io.polymorphicpanda.kspec.matcher.*
import kspec.It
import kotlin.reflect.KClass

fun <T> It.expect(arg: T) = Expect(arg)
fun <T> It.eq(expected: T, message: String? = null) = Equals(expected, message)
fun <T> It.notEq(expected: T, message: String? = null) = NotEquals(expected, message)
fun <T> It.same(expected: T, message: String? = null) = Same(expected, message)
fun <T> It.notSame(expected: T, message: String? = null) = NotSame(expected, message)
fun It.truthy(message: String? = null) = Equals(true, message)
fun It.falsy(message: String? = null) = Equals(false, message)

fun <T> Expect<T?>.toBeNull(message: String? = null) = Equals<T?>(null, message)
fun <T> Expect<T?>.toBeNotNull(message: String? = null) = NotEquals<T?>(null, message)

fun It.thrown(expected: KClass<out Throwable>) = Thrown(expected.java)
fun It.notThrown(expected: KClass<out Throwable>? = null) = NotThrown(expected?.java)


fun <T> It.eq(expected: Collection<T>,
              strict: Boolean = false, message: String? = null) = ContentEquals(expected, strict, message)
fun <T> It.notEq(expected: Collection<T>, message: String? = null) = ContentNotEquals(expected, message)
