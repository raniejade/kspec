package io.polymorphicpanda.kspec.junit

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.junit.support.RememberingListener
import io.polymorphicpanda.kspec.setupTestSpec
import kspec.KSpec
import kspec.context
import kspec.describe
import kspec.it
import org.junit.Assert
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
                                    assertThat(true, equalTo(false))
                                }

                                it("another failure") {
                                    Assert.fail()
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
                        assertThat(listener!!.isStarted(test1), equalTo(true))
                        assertThat(listener!!.isStarted(test2), equalTo(true))
                        assertThat(listener!!.isStarted(test3), equalTo(true))
                    }

                    it("should notify test finished") {
                        assertThat(listener!!.isFinished(test1), equalTo(true))
                        assertThat(listener!!.isFinished(test2), equalTo(true))
                        assertThat(listener!!.isFinished(test3), equalTo(true))
                    }

                    it("should notify failures") {
                        assertThat(listener!!.hasFailed(test1), equalTo(true))
                        assertThat(listener!!.hasFailed(test2), equalTo(true))
                        assertThat(listener!!.hasFailed(test3), equalTo(false))
                    }

                }


                describe("beforeEach") {
                    it("should be invoked for each terminal context") {
                        assertThat(beforeEachCounter, equalTo(testCount))
                    }
                }

                describe("afterEach") {
                    it("should be invoked for each terminal context") {
                        assertThat(afterEachCounter, equalTo(testCount))
                    }
                }

                describe("before") {
                    it("should be invoked only once") {
                        assertThat(beforeCounter, equalTo(1))
                    }
                }

                describe("after") {
                    it("should be invoked only once") {
                        assertThat(afterCounter, equalTo(1))
                    }
                }
            }
        }
    }
}
