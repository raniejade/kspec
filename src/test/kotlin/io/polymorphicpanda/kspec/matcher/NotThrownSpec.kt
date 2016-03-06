package io.polymorphicpanda.kspec.matcher

import kspec.KSpec
import java.io.IOException

/**
 * @author Ranie Jade Ramiso
 */
class NotThrownSpec: KSpec() {
    override fun spec() {
        describe("NotThrown") {
            context("no exception thrown expected") {
                var matcher = NotThrown(null)

                describe("match") {
                    context("block throws an exception") {
                        it("should throw an AssertionError") {
                            try {
                                matcher.match { throw RuntimeException() }
                                fail()
                            } catch (e: AssertionError) { }
                        }
                    }

                    context("block does not throw any exception") {
                        it("should not throw an AssertionError") {
                            try {
                                matcher.match { }
                            } catch (e: AssertionError) {
                                fail()
                            }
                        }
                    }
                }
            }

            context("a specific exception not be thrown is expected") {
                var matcher = NotThrown(IOException::class.java)

                describe("match") {
                    context("block does not throw any exception") {
                        it("should not throw an AssertionError") {
                            try {
                                matcher.match { }
                            } catch (e: AssertionError) {
                                fail()
                            }
                        }
                    }

                    context("block throws an exception but not the expected") {
                        it("should not throw an AssertionError") {
                            try {
                                matcher.match { throw RuntimeException() }
                            } catch (e: AssertionError) {
                                fail()
                            }
                        }
                    }

                    context("block throws the expected exception") {
                        it("should throw an AssertionError") {
                            try {
                                matcher.match { throw IOException() }
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
