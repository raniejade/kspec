package kspec.asserts

import io.polymorphicpanda.kspec.matcher.*
import kspec.It
import kotlin.reflect.KClass

fun <T> It.expect(arg: T) = Expect(arg)
fun <T> It.eq(expected: T, message: Any?) = Equals(expected, message)
fun <T> It.notEq(expected: T, message: Any?)= NotEquals(expected, message)
fun <T> It.same(expected: T, message: Any?) = Same(expected, message)
fun <T> It.notSame(expected: T, message: Any?) = NotSame(expected, message)
fun It.truthy(message: Any?) = Equals(true, message)
fun It.falsy(message: Any?) = Equals(false, message)


fun It.expect(arg: () -> Unit) = Expect<() -> Unit, BlockMatcher>(arg)
fun Expect<() -> Unit, BlockMatcher>.toThrow(expected: KClass<out Throwable>) = Throw(expected.java)
fun It.`throw`(expected: KClass<out Throwable>) = Throw(expected.java)
fun It.notThrow(expected: KClass<out Throwable>? = null) = NotThrow(expected?.java)
