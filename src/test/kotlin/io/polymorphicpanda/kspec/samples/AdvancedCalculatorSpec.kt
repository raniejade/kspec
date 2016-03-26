package io.polymorphicpanda.kspec.samples

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import kspec.*

/**
 * @author Ranie Jade Ramiso
 */
class AdvancedCalculatorSpec: KSpec() {
    override fun spec() {
        describe(AdvancedCalculator::class) {
            itBehavesLike(advancedCalculator())
        }
    }

    companion object {
        fun advancedCalculator() = sharedExample<AdvancedCalculator> {
            itBehavesLike(CalculatorSpec.calculator())

            describe("pow") {
                it("2 ^ 3 = 8") {
                    assertThat(subject.pow(2.0, 3.0), equalTo(8.0))
                }
            }

            describe("sqrt") {
                xit("sqrt(4)", "Not implemented yet") {
                    assertThat(subject.sqrt(4.0), equalTo(2.0))
                }
            }

            describe("tan") {
                xit("tan(2)",  "TBD")
            }
        }
    }
}
