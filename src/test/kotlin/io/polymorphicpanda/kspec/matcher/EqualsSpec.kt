package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import kspec.asserts.expect
import kspec.asserts.notThrown
import kspec.asserts.thrown

/**
 * @author Ranie Jade Ramiso
 */
class EqualsSpec: KSpec() {
    override fun spec() {
        describe("Equals") {
            val matcher = Equals(1, null)

            describe("match") {
                context("passed argument is not equal to expected") {
                    it("should throw an AssertionError") {
                        expect({
                            matcher.match(2)
                        }).toBe(thrown(AssertionError::class))
                    }
                }

                context("passed argument is equal to expected") {
                    it("should not throw an exception") {
                        expect({
                            matcher.match(1)
                        }).toBe(notThrown())
                    }
                }
            }
        }
    }
}
