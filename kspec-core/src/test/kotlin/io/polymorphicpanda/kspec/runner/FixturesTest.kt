package io.polymorphicpanda.kspec.runner

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.context
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.support.setupSpec
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class FixturesTest {
    @Test
    fun testBefore() {
        var counter = 0;

        val spec = setupSpec {
            before {
                counter++
            }

            describe("a group") {
                before {
                    counter++
                }

                context("another group") {
                    before {
                        counter++
                    }

                    it("an example") {

                    }

                    it("another example") {

                    }
                }

                it("yet another example") {

                }
            }

            it("oh no.. another example") {

            }
        }

        val runner = KSpecRunner(spec)
        val notifier = RunNotifier()

        runner.run(notifier)

        assertThat(counter, equalTo(3))
    }

    @Test
    fun testBeforeEach() {
        var counter = 0;

        val spec = setupSpec {
            beforeEach {
                counter++
            }

            describe("a group") {
                beforeEach {
                    counter++
                }

                context("another group") {
                    beforeEach {
                        counter++
                    }

                    it("an example") {

                    }

                    it("another example") {

                    }
                }

                it("yet another example") {

                }
            }

            it("oh no.. another example") {

            }
        }

        val runner = KSpecRunner(spec)
        val notifier = RunNotifier()

        runner.run(notifier)

        assertThat(counter, equalTo(9))
    }

    @Test
    fun testAfter() {
        var counter = 0;

        val spec = setupSpec {
            after {
                counter++
            }

            describe("a group") {
                after {
                    counter++
                }

                context("another group") {
                    after {
                        counter++
                    }

                    it("an example") {

                    }

                    it("another example") {

                    }
                }

                it("yet another example") {

                }
            }

            it("oh no.. another example") {

            }
        }

        val runner = KSpecRunner(spec)
        val notifier = RunNotifier()

        runner.run(notifier)

        assertThat(counter, equalTo(3))
    }

    @Test
    fun testAfterEach() {
        var counter = 0;

        val spec = setupSpec {
            afterEach {
                counter++
            }

            describe("a group") {
                afterEach {
                    counter++
                }

                context("another group") {
                    afterEach {
                        counter++
                    }

                    it("an example") {

                    }

                    it("another example") {

                    }
                }

                it("yet another example") {

                }
            }

            it("oh no.. another example") {

            }
        }

        val runner = KSpecRunner(spec)
        val notifier = RunNotifier()

        runner.run(notifier)

        assertThat(counter, equalTo(9))
    }
}
