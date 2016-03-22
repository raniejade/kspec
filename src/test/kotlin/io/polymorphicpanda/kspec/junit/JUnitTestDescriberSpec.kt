package io.polymorphicpanda.kspec.junit

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.polymorphicpanda.kspec.context.GroupContext
import io.polymorphicpanda.kspec.setupTestSpec
import kspec.KSpec
import kspec.context
import kspec.describe
import kspec.it

/**
 * @author Ranie Jade Ramiso
 */
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


            context("spec description") {
                var spec: GroupContext?

                beforeEach {
                    spec = setupTestSpec {
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

                    spec!!.visit(describer)
                }

                it("group context should have the following format: <context-name>: <context-description>") {

                    assertThat(containsDescription("describe: description1"), equalTo(true))
                    assertThat(containsDescription("context: context1"), equalTo(true))
                    assertThat(containsDescription("context: context2"), equalTo(true))

                }

                it("terminal context should have the following format: <context-name>: <context-description>(parent(1..n)*.description") {
                    assertThat(containsDescription("it: it1(kspec.KSpec.describe: description1.context: context1)"), equalTo(true))
                    assertThat(containsDescription("it: it2(kspec.KSpec.describe: description1)"), equalTo(true))
                    assertThat(containsDescription("it: it3(kspec.KSpec)"), equalTo(true))
                }
            }
        }
    }
}
