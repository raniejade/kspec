package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import java.io.IOException
import kotlin.test.fail

/**
 * @author Ranie Jade Ramiso
 */
class ThrowSpec: KSpec() {
    override fun spec() {
        describe("Throw") {
            context("any exception expected") {
                var matcher = Throw(null)

                describe("match") {
                    context("block throws an exception") {
                        it("should not throw an AssertionError") {
                            try {
                                matcher.match { throw RuntimeException() }
                            } catch (e: AssertionError) {
                                fail()
                            }
                        }
                    }

                    context("block does not throw any exception") {
                        it("should throw an AssertionError") {
                            try {
                                matcher.match { }
                                fail()
                            } catch (e: AssertionError) {
                            }
                        }
                    }
                }
            }

            context("a specific exception expected") {
                var matcher = Throw(IOException::class.java)

                describe("match") {
                    context("block throws an exception but not the expected") {
                        it("should throw an AssertionError") {
                            try {
                                matcher.match { throw RuntimeException() }
                                fail()
                            } catch (e: AssertionError) {
                            }
                        }
                    }

                    context("block throws expected exception") {
                        it("should not throw an AssertionError") {
                            try {
                                matcher.match { throw IOException() }
                            } catch (e: AssertionError) {
                                fail()
                            }
                        }
                    }

                    context("block does not throw any exception") {
                        it("should throw an AssertionError") {
                            try {
                                matcher.match { }
                                fail()
                            } catch (e: AssertionError) {
                            }
                        }
                    }
                }
            }
        }
    }
}
