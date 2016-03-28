package io.polymorphicpanda.kspec

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.runner.KSpecRunner
import io.polymorphicpanda.kspec.runner.RunNotifier
import io.polymorphicpanda.kspec.support.setupSpec
import org.junit.Test

/**
 * @author Ranie Jade Ramiso
 */
class SharedExampleTest {
    @Test
    fun testSubjectUniqueForEachExample() {

        var subject1: Throwable? = null
        var subject2: Throwable? = null


        val shared = sharedExample<Throwable> {
            it("an example") {
                subject1 = subject
            }

            it("another example") {
                subject2 = subject
            }
        }

        val spec = setupSpec {
            describe(RuntimeException::class) {
                subject { RuntimeException() }
                itBehavesLike(shared)
            }
        }

        val notifier = RunNotifier()
        val runner = KSpecRunner(spec)
        runner.run(notifier)

        assertThat(subject1 !== subject2, equalTo(true))
    }
}
