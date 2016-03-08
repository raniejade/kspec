package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.junit.support.RememberingListener
import io.polymorphicpanda.kspec.setupTestSpec
import kspec.KSpec
import kspec.asserts.eq
import kspec.asserts.expect
import kspec.asserts.falsy
import kspec.asserts.truthy
import org.junit.runner.notification.RunNotifier

/**
 * @author Ranie Jade Ramiso
 */
class JUnitTestExecutorSpec: KSpec() {
    override fun spec() {
        describe("JUnitTestExecutor") {
            var listener: RememberingListener? = null
            var notifier: RunNotifier? = null
            var describer: JUnitTestDescriber? = null
            var root: Context?
            var executor: JUnitTestExecutor?

            beforeEach {
                listener = RememberingListener()
                notifier = RunNotifier()
                notifier!!.addListener(listener)
                describer = JUnitTestDescriber()
            }


            describe("test execution") {
                val test1 = "it: should fail(kspec.KSpec.describe: a description.context: this happen)"
                val test2 = "it: another failure(kspec.KSpec.describe: a description.context: this happen)"
                val test3 = "it: this will not fail(kspec.KSpec.describe: a description)"
                val testCount = 3

                var beforeCounter = 0
                var afterCounter = 0
                var beforeEachCounter = 0
                var afterEachCounter = 0

                beforeEach {
                    beforeCounter = 0
                    afterCounter = 0
                    beforeEachCounter = 0
                    afterEachCounter = 0

                    root = setupTestSpec {
                        describe("a description") {
                            before { beforeCounter++ }
                            beforeEach { beforeEachCounter++ }
                            context("this happen") {
                                it("should fail") {
                                    expect(1).toBe(eq(0))
                                }

                                it("another failure") {
                                    fail()
                                }
                            }

                            it("this will not fail") {
                                // pass
                            }
                            afterEach { afterEachCounter++ }
                            after { afterCounter++ }
                        }
                    }
                    root!!.visit(describer!!)
                    executor =JUnitTestExecutor(notifier!!, describer!!.contextDescriptions)

                    root!!.visit(executor!!)
                }

                describe("lifecycle notifications") {
                    it("should notify test starts") {
                        expect(listener!!.isStarted(test1)).toBe(truthy())
                        expect(listener!!.isStarted(test2)).toBe(truthy())
                        expect(listener!!.isStarted(test3)).toBe(truthy())
                    }

                    it("should notify test finished") {
                        expect(listener!!.isFinished(test1)).toBe(truthy())
                        expect(listener!!.isFinished(test2)).toBe(truthy())
                        expect(listener!!.isFinished(test3)).toBe(truthy())
                    }

                    it("should notify failures") {
                        expect(listener!!.hasFailed(test1)).toBe(truthy())
                        expect(listener!!.hasFailed(test2)).toBe(truthy())
                        expect(listener!!.hasFailed(test3)).toBe(falsy())
                    }

                }


                describe("beforeEach") {
                    it("should be invoked for each terminal context") {
                        expect(beforeEachCounter).toBe(eq(testCount))
                    }
                }

                describe("afterEach") {
                    it("should be invoked for each terminal context") {
                        expect(afterEachCounter).toBe(eq(testCount))
                    }
                }

                describe("before") {
                    it("should be invoked only once") {
                        expect(beforeCounter).toBe(eq(1))
                    }
                }

                describe("after") {
                    it("should be invoked only once") {
                        expect(afterCounter).toBe(eq(1))
                    }
                }
            }
        }
    }
}
