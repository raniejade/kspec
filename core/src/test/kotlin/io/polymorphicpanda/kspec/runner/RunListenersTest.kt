package io.polymorphicpanda.kspec.runner

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.context
import io.polymorphicpanda.kspec.context.ExampleContext
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import io.polymorphicpanda.kspec.describe
import io.polymorphicpanda.kspec.it
import io.polymorphicpanda.kspec.support.setupSpec
import io.polymorphicpanda.kspec.xit
import org.junit.Test
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class RunListenersTest {
    class RememberingListener: RunListener {
        val groupStarted = HashMap<String, ExampleGroupContext>()
        val groupFinished = HashMap<String, ExampleGroupContext>()
        val groupFailure = HashMap<String, ExampleGroupContext>()

        val exampleStarted = HashMap<String, ExampleContext>()
        val exampleFinished = HashMap<String, ExampleContext>()
        val exampleFailure = HashMap<String, ExampleContext>()
        val exampleIgnored = HashMap<String, ExampleContext>()

        override fun exampleStarted(example: ExampleContext) {
            exampleStarted.put(example.description, example)
        }

        override fun exampleFailure(example: ExampleContext, failure: Throwable) {
            exampleFailure.put(example.description, example)
        }

        override fun exampleFinished(example: ExampleContext) {
            exampleFinished.put(example.description, example)
        }

        override fun exampleIgnored(example: ExampleContext) {
            exampleIgnored.put(example.description, example)
        }

        override fun exampleGroupStarted(group: ExampleGroupContext) {
            groupStarted.put(group.description, group)
        }

        override fun exampleGroupFailure(group: ExampleGroupContext, failure: Throwable) {
            groupFailure.put(group.description, group)
        }

        override fun exampleGroupFinished(group: ExampleGroupContext) {
            groupFinished.put(group.description, group)
        }
    }

    @Test
    fun testNotify() {
        val spec = setupSpec {
            describe("a group") {
                context("another group") {
                    it("an example") {

                    }

                    it("another example") {

                    }

                    describe("yet another group") {
                        before {
                            throw RuntimeException()
                        }

                        it("yet another example") {

                        }
                    }

                    it("oh no.. another example") {
                        throw RuntimeException()
                    }

                    xit("oh no.. yet another example")
                }
            }
        }

        val runner = KSpecRunner(spec)
        val listener = RememberingListener()
        val notifier = RunNotifier()
        notifier.addListener(listener)

        runner.run(notifier)

        assertThat(listener.groupStarted.containsKey("describe: a group"), equalTo(true))
        assertThat(listener.groupStarted.containsKey("context: another group"), equalTo(true))
        assertThat(listener.groupStarted.containsKey("describe: yet another group"), equalTo(true))

        assertThat(listener.groupFailure.containsKey("describe: a group"), equalTo(false))
        assertThat(listener.groupFailure.containsKey("context: another group"), equalTo(false))
        assertThat(listener.groupFailure.containsKey("describe: yet another group"), equalTo(true))

        assertThat(listener.groupFinished.containsKey("describe: a group"), equalTo(true))
        assertThat(listener.groupFinished.containsKey("context: another group"), equalTo(true))
        assertThat(listener.groupFinished.containsKey("describe: yet another group"), equalTo(true))

        assertThat(listener.exampleStarted.containsKey("it: an example"), equalTo(true))
        assertThat(listener.exampleStarted.containsKey("it: another example"), equalTo(true))
        assertThat(listener.exampleStarted.containsKey("it: yet another example"), equalTo(false))
        assertThat(listener.exampleStarted.containsKey("it: oh no.. another example"), equalTo(true))
        assertThat(listener.exampleStarted.containsKey("it: oh no.. yet another example"), equalTo(false))

        assertThat(listener.exampleFailure.containsKey("it: an example"), equalTo(false))
        assertThat(listener.exampleFailure.containsKey("it: another example"), equalTo(false))
        assertThat(listener.exampleFailure.containsKey("it: yet another example"), equalTo(false))
        assertThat(listener.exampleFailure.containsKey("it: oh no.. another example"), equalTo(true))
        assertThat(listener.exampleFailure.containsKey("it: oh no.. yet another example"), equalTo(false))

        assertThat(listener.exampleIgnored.containsKey("it: an example"), equalTo(false))
        assertThat(listener.exampleIgnored.containsKey("it: another example"), equalTo(false))
                assertThat(listener.exampleIgnored.containsKey("it: yet another example"), equalTo(false))
        assertThat(listener.exampleIgnored.containsKey("it: oh no.. another example"), equalTo(false))
        assertThat(listener.exampleIgnored.containsKey("it: oh no.. yet another example"), equalTo(true))

        assertThat(listener.exampleFinished.containsKey("it: an example"), equalTo(true))
        assertThat(listener.exampleFinished.containsKey("it: another example"), equalTo(true))
        assertThat(listener.exampleFinished.containsKey("it: yet another example"), equalTo(false))
        assertThat(listener.exampleFinished.containsKey("it: oh no.. another example"), equalTo(true))
        assertThat(listener.exampleFinished.containsKey("it: oh no.. yet another example"), equalTo(false))

    }
}
