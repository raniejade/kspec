package io.polymorphicpanda.kspec.samples

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.*
import io.polymorphicpanda.kspec.junit.JUnitKSpecRunner
import org.junit.runner.RunWith

/**
 * @author Ranie Jade Ramiso
 */
@RunWith(JUnitKSpecRunner::class)
class CalculatorSpec: KSpec() {
    override fun spec() {
        describe(Calculator::class) {
            itBehavesLike(calculator())
        }
    }

    companion object {
        fun calculator() = sharedExample<Calculator> {
            describe("add") {
                it("1 + 1 = 2") {
                    assertThat(subject.add(1, 1), equalTo(2))
                }
            }

            describe("minus") {
                it("1 - 1 = 0") {
                    assertThat(subject.minus(1, 1), equalTo(0))
                }
            }

            describe("multiply") {
                it("1 * 2 = 2") {
                    assertThat(subject.multiply(1, 2), equalTo(2))
                }
            }

            describe("divide") {
                it("10 / 5 = 2") {
                    assertThat(subject.divide(10, 5), equalTo(2))
                }
            }
        }
    }
}
