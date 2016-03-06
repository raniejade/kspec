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
                val test2 = "it: this will not fail(kspec.KSpec.describe: a description)"
                beforeEach {
                    root = setupTestSpec {
                        describe("a description") {
                            context("this happen") {
                                it("should fail") {
                                    expect(1).toBe(eq(0))
                                }
                            }

                            it("this will not fail") {
                                // pass
                            }
                        }
                    }
                    root!!.visit(describer!!)
                    executor =JUnitTestExecutor(notifier!!, describer!!.contextDescriptions)

                    root!!.visit(executor!!)
                }

                it("should notify test starts") {
                    expect(listener!!.isStarted(test1)).toBe(truthy())
                    expect(listener!!.isStarted(test2)).toBe(truthy())
                }

                it("should notify test finished") {
                    expect(listener!!.isFinished(test1)).toBe(truthy())
                    expect(listener!!.isFinished(test2)).toBe(truthy())
                }

                it("should notify failures") {
                    expect(listener!!.hasFailed(test1)).toBe(truthy())
                    expect(listener!!.hasFailed(test2)).toBe(falsy())
                }
            }
        }
    }
}
