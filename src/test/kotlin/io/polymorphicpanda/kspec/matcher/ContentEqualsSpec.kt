package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import kspec.asserts.expect
import kspec.asserts.notThrown
import kspec.asserts.thrown

/**
 * @author Ranie Jade Ramiso
 */
class ContentEqualsSpec: KSpec() {
    override fun spec() {
        describe("ContentEquals") {
            describe("match") {
                describe("strict compare") {
                    val matcher = ContentEquals(listOf(1, 2), true, null)

                    context("equal list - same order") {
                        it("should not throw an exception") {
                            expect({
                                matcher.match(listOf(1, 2))
                            }).toBe(notThrown())
                        }
                    }

                    context("equal list - not same order") {
                        it("should throw an exception") {
                            expect({
                                matcher.match(listOf(2, 1))
                            }).toBe(thrown(AssertionError::class))
                        }
                    }
                }

                describe("non-strict compare") {
                    val matcher = ContentEquals(listOf(1, 2), false, null)

                    context("equal list - same order") {
                        it("should not throw an exception") {
                            expect({
                                matcher.match(listOf(1, 2))
                            }).toBe(notThrown())
                        }
                    }

                    context("equal list - not same order") {
                        it("should throw an exception") {
                            expect({
                                matcher.match(listOf(2, 1))
                            }).toBe(notThrown())
                        }
                    }
                }

                context("list not equals") {
                    val matcher = ContentEquals(listOf(1, 2), true, null)
                    it("should throw an exception") {
                        expect({
                            matcher.match(listOf(2, 1))
                        }).toBe(thrown(AssertionError::class))
                    }
                }
            }

        }
    }
}
