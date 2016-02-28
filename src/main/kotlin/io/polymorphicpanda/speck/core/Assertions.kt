package io.polymorphicpanda.speck.core

import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When
import kotlin.reflect.KClass


internal class Assertions(val feature: Feature): Then {
    var tracker: AssertionOperator? = null

    private fun AssertionOperator.remember(): AssertionOperator {
        if (tracker != null) {
            this.attach(tracker!!)
        }
        tracker = this
        return this;
    }

    override fun <T> shouldBeEqual(expected: T?, actual: T?) {
        ShouldBeEqualAssertion(feature, expected, actual)
            .remember()
            .assert()
    }

    override fun <T> shouldNotBeEqual(expected: T?, actual: T?) {
        ShouldNotBeEqualAssertion(feature, expected, actual)
            .remember()
            .assert()
    }

    override fun <T> shouldBeSame(expected: T?, actual: T?) {
        ShouldBeSameAssertion(feature, expected, actual)
            .remember()
            .assert()
    }

    override fun <T> shouldNotBeSame(expected: T?, actual: T?) {
        ShouldNotBeSameAssertion(feature, expected, actual)
            .remember()
            .assert()
    }

    override fun shouldBeTrue(expression: Boolean) {
        ShouldBeTrueAssertion(feature, expression)
            .remember()
            .assert()
    }

    override fun shouldBeFalse(expression: Boolean) {
        ShouldBeFalseAssertion(feature, expression)
            .remember()
            .assert()
    }

    override fun shouldThrow(invoke: () -> Unit) {
        ShouldThrowAssertion(feature, null, invoke)
            .remember()
            .assert()
    }

    override fun shouldThrow(expected: KClass<out Throwable>, invoke: () -> Unit) {
        ShouldThrowAssertion(feature, expected, invoke)
            .remember()
            .assert()
    }
}

internal data class Feature(val given: Action<Given>,
                            val `when`: Action<When>,
                            val then: Action<Then>)

internal abstract class AssertionOperator(val feature: Feature) {

    var previous: AssertionOperator? = null

    fun assert() {
        assert(doAssert(), {
            var str = """

     ${feature.given.description()}
       ${feature.`when`.description()}
         ${feature.then.description()}"""
            collect(this).reversed().forEach {
                str += """
           * $it"""
            }
            str + " // assertion failed here\n"
        })
    }

    fun attach(previous: AssertionOperator) {
        this.previous = previous
    }

    abstract protected fun doAssert(): Boolean

    abstract protected fun prettyPrint(): String

    companion object {
        fun collect(assertion: AssertionOperator): List<String> {
            if (assertion.previous != null) {
                val result = mutableListOf(assertion.prettyPrint())
                result.addAll(collect(assertion.previous!!))
                return result
            }
            return listOf(assertion.prettyPrint())
        }
    }
}

internal abstract class AssertionOperator1<P1>(feature: Feature,
                                               val p1: P1?): AssertionOperator(feature)

internal abstract class AssertionOperator2<P1, P2>(feature: Feature,
                                                   val p1: P1?, val p2: P2?): AssertionOperator(feature) {

    abstract protected fun operation(): String
    override fun prettyPrint(): String = "${operation()}($p1, $p2)"
}

internal class ShouldBeEqualAssertion<P1, P2>(feature: Feature,
                                              p1: P1?,
                                              p2: P2?): AssertionOperator2<P1, P2>(feature, p1, p2) {
    override fun operation(): String = "shouldBeEqual"
    override fun doAssert(): Boolean = p1 == p2
}

internal class ShouldNotBeEqualAssertion<P1, P2>(feature: Feature,
                                                 p1: P1?,
                                                 p2: P2?): AssertionOperator2<P1, P2>(feature, p1, p2) {
    override fun operation(): String = "shouldNotBeEqual"
    override fun doAssert(): Boolean = p1 != p2
}

internal class ShouldBeSameAssertion<P1, P2>(feature: Feature,
                                             p1: P1?,
                                             p2: P2?): AssertionOperator2<P1, P2>(feature, p1, p2) {
    override fun operation(): String = "shouldBeSame"
    override fun doAssert(): Boolean = p1 === p2
}

internal class ShouldNotBeSameAssertion<P1, P2>(feature: Feature,
                                                p1: P1?,
                                                p2: P2?): AssertionOperator2<P1, P2>(feature, p1, p2) {
    override fun operation(): String = "shouldNotBeSame"
    override fun doAssert(): Boolean = p1 !== p2
}

internal class ShouldBeTrueAssertion(feature: Feature,
                                     expression: Boolean): AssertionOperator1<Boolean>(feature, expression) {
    override fun doAssert(): Boolean = p1!!
    override fun prettyPrint(): String = "shouldBeTrue($p1)"
}

internal class ShouldBeFalseAssertion(feature: Feature,
                                     expression: Boolean): AssertionOperator1<Boolean>(feature, expression) {
    override fun doAssert(): Boolean = !p1!!
    override fun prettyPrint(): String = "shouldBeFalse($p1}"
}

internal class ShouldThrowAssertion(feature: Feature,
                                    expected: KClass<out Throwable>?,
                                    val action: () -> Unit)
    : AssertionOperator1<KClass<out Throwable>>(feature, expected) {

    override fun doAssert(): Boolean {
        try {
            action()
        } catch (e: Throwable) {
            if (p1 != null) {
                return e.javaClass == p1
            }
            return true
        }
        return false
    }

    override fun prettyPrint(): String {
        return if (p1 != null) {
            "shouldThrow(${p1.qualifiedName}) { ... }"
        } else {
            "shouldThrow { ... }"
        }
    }
}
