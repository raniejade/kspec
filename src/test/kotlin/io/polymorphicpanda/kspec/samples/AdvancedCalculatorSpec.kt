package io.polymorphicpanda.kspec.samples

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import kspec.KSpec
import kspec.describe
import kspec.it
import kspec.sharedExample

/**
 * @author Ranie Jade Ramiso
 */
class AdvancedCalculatorSpec: KSpec() {
    override fun spec() {
        describe(AdvancedCalculator::class) {
            behavesLike(advancedCalculator())
        }
    }

    companion object {
        fun advancedCalculator() = sharedExample<AdvancedCalculator> {
            behavesLike(CalculatorSpec.calculator())

            describe("pow") {
                it("2 ^ 3 = 8") {
                    assertThat(subject.pow(2.0, 3.0), equalTo(8.0))
                }
            }
        }
    }
}
