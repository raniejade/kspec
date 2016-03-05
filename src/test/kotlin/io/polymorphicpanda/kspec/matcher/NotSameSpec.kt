package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import kspec.asserts.expect
import kspec.asserts.notThrow
import kspec.asserts.toThrow

/**
 * @author Ranie Jade Ramiso
 */
class NotSameSpec: KSpec() {
    override fun spec() {
        describe("NotSame") {
            val instance = listOf(1)
            val matcher = NotSame(instance, null)

            describe("match") {
                context("passed argument is not same instance") {
                    it("should not throw an exception") {
                        expect({
                            matcher.match(listOf(1))
                        }).to(notThrow())
                    }
                }

                context("passed argument is same instance") {
                    it("should throw an AssertionError") {
                        expect({
                            matcher.match(instance)
                        }).toThrow(AssertionError::class)
                    }
                }
            }
        }
    }
}
