package io.polymorphicpanda.kspec.junit

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.*
import io.polymorphicpanda.kspec.context.ExampleGroupContext
import org.junit.runner.RunWith

/**
 * @author Ranie Jade Ramiso
 */
@RunWith(JUnitKSpecRunner::class)
class JUnitTestDescriberSpec: KSpec() {

    override fun spec() {
        describe("JUnitTestDescriber") {
            val describer = JUnitTestDescriber()

            fun containsDescription(description: String): Boolean {
                return describer.contextDescriptions.filterValues {
                    it.displayName.equals(description)
                }.isNotEmpty()
            }

            beforeEach {
                describer.contextDescriptions.clear()
            }


            context(ExampleGroupContext::class, "spec description") {

                subject {
                    return@subject setupTestSpec {
                        describe("description1") {
                            context("context1") {
                                it("it1") {

                                }
                            }

                            it("it2") {

                            }
                        }

                        context("context2") {

                        }

                        it("it3") {

                        }
                    }
                }

                beforeEach {
                    subject.visit(describer)
                }

                it("group context should have the following format: <context-name>: <context-description>") {

                    assertThat(containsDescription("describe: description1"), equalTo(true))
                    assertThat(containsDescription("context: context1"), equalTo(true))
                    assertThat(containsDescription("context: context2"), equalTo(true))

                }

                it("terminal context should have the following format: <context-name>: <context-description>(parent(1..n)*.description") {
                    assertThat(containsDescription("it: it1(io.polymorphicpanda.kspec.TestSpec.describe: description1.context: context1)"), equalTo(true))
                    assertThat(containsDescription("it: it2(io.polymorphicpanda.kspec.TestSpec.describe: description1)"), equalTo(true))
                    assertThat(containsDescription("it: it3(io.polymorphicpanda.kspec.TestSpec)"), equalTo(true))
                }
            }
        }
    }
}
