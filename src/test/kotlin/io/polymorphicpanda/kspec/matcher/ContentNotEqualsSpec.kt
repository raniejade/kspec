package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import kspec.asserts.expect
import kspec.asserts.notThrown
import kspec.asserts.thrown

/**
 * @author Ranie Jade Ramiso
 */
class ContentNotEqualsSpec: KSpec() {
    override fun spec() {
        describe("ContentEquals") {
            describe("match") {
                val matcher = ContentNotEquals(listOf(1, 2), null)

                context("list not equals") {
                    it("should throw an exception") {
                        expect({
                            matcher.match(listOf(2, 3, 1))
                        }).toBe(notThrown())
                    }
                }

                context("list equals - not same order") {
                    it("should throw an exception") {
                        expect({
                            matcher.match(listOf(2, 1))
                        }).toBe(thrown(AssertionError::class))
                    }
                }

                context("list equals - same order") {
                    it("should throw an exception") {
                        expect({
                            matcher.match(listOf(1, 2))
                        }).toBe(thrown(AssertionError::class))
                    }
                }
            }

        }
    }
}
