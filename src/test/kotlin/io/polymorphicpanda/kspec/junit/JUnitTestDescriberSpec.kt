package io.polymorphicpanda.kspec.junit

import io.polymorphicpanda.kspec.context.Context
import io.polymorphicpanda.kspec.setupTestSpec
import kspec.KSpec
import kspec.asserts.expect
import kspec.asserts.truthy

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
                var spec: Context?

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

                    expect(
                            containsDescription("describe: description1")
                    ).toBe(truthy())

                    expect(
                            containsDescription("context: context1")
                    ).toBe(truthy())


                    expect(
                            containsDescription("context: context2")
                    ).toBe(truthy())


                }

                it("terminal context should have the following format: <context-name>: <context-description>(parent(1..n)*.description") {
                    expect(
                            containsDescription("it: it1(kspec.KSpec.describe: description1.context: context1)")
                    ).toBe(truthy())

                    expect(
                            containsDescription("it: it2(kspec.KSpec.describe: description1)")
                    ).toBe(truthy())

                    expect(
                            containsDescription("it: it3(kspec.KSpec)")
                    ).toBe(truthy())
                }
            }
        }
    }
}
