package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import kspec.asserts.expect
import kspec.asserts.notThrow
import kspec.asserts.toThrow

/**
 * @author Ranie Jade Ramiso
 */
class SameSpec: KSpec() {
    override fun spec() {
        describe("Same") {
            val instance = listOf(1)
            val matcher = Same(instance, null)

            describe("match") {
                context("passed argument is not same instance") {
                    it("should throw an AssertionError") {
                        expect({
                            matcher.match(listOf(1))
                        }).toThrow(AssertionError::class)
                    }
                }

                context("passed argument is same instance") {
                    it("should not throw an exception") {
                        expect({
                            matcher.match(instance)
                        }).to(notThrow())
                    }
                }
            }
        }
    }
}
