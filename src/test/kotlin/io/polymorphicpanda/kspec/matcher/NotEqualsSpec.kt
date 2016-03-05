package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import kspec.asserts.expect
import kspec.asserts.notThrown
import kspec.asserts.thrown

/**
 * @author Ranie Jade Ramiso
 */
class NotEqualsSpec: KSpec() {
    override fun spec() {
        describe("NotEquals") {
            val matcher = NotEquals(1, null)

            describe("match") {
                context("passed argument is not equal to expected") {
                    it("should not throw an exception") {
                        expect({
                            matcher.match(2)
                        }).toBe(notThrown())
                    }
                }

                context("passed argument is equal to expected") {
                    it("should throw an exception") {
                        expect({
                            matcher.match(1)
                        }).toBe(thrown(AssertionError::class))
                    }
                }
            }
        }
    }
}
