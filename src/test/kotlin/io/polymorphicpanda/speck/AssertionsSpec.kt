package io.polymorphicpanda.speck

import java.io.IOException

/**
 * @author Ranie Jade Ramiso
 */
class AssertionsSpec: Speck({
    Given("shouldBeEqual") {
        BeforeWhen { println("before $it") }
        When("invoked with equal values") {
            Then("it should not throw an assertion error") {
                try {
                    shouldBeEqual(true, true)
                } catch(e: Throwable) {
                    throw AssertionError()
                }
            }
        }

        When("invoked with not equal values") {
            Then("it should throw an assertion error") {
                try {
                    shouldBeEqual(true, false)
                    throw AssertionError()
                } catch (e: Throwable) {
                    // passing
                }
            }
        }

        AfterWhen { println("after $it") }
    }

    Given("shouldNotBeEqual") {
        When("invoked with equal values") {
            Then("it should throw an assertion error") {
                try {
                    shouldNotBeEqual(true, true)
                    throw AssertionError()
                } catch (e: Throwable) {
                    // passing
                }
            }
        }

        When("invoked with not equal values") {
            Then("it should not throw an assertion error") {
                try {
                    shouldNotBeEqual(true, false)
                } catch(e: Throwable) {
                    throw AssertionError()
                }
            }
        }
    }

    Given("shouldBeTrue") {
        When("invoked with false expression") {
            Then("it should throw an assertion error") {
                try {
                    shouldBeTrue(false)
                    throw AssertionError()
                } catch (e: Throwable) {
                    // passing
                }
            }
        }

        When("invoked with true expression") {
            Then("it should not throw an assertion error") {
                try {
                    shouldBeTrue(true)
                } catch(e: Throwable) {
                    throw AssertionError()
                }
            }
        }
    }

    Given("shouldBeFalse") {
        When("invoked with true expression") {
            Then("it should throw an assertion error") {
                try {
                    shouldBeFalse(true)
                    throw AssertionError()
                } catch (e: Throwable) {
                    // passing
                }
            }
        }

        When("invoked with false expression") {
            Then("it should not throw an assertion error") {
                try {
                    shouldBeFalse(false)
                } catch(e: Throwable) {
                    throw AssertionError()
                }
            }
        }
    }

    Given("shouldThrow") {
        When("block does not throw an exception") {
            Then("it should throw an assertion error") {
                try {
                    shouldThrow {
                        // do nothing
                    }
                    throw AssertionError()
                } catch(e: Throwable) {
                    // success
                }
            }
        }

        When("block does throw an exception") {
            Then("it should not throw an assertion error") {
                try {
                    shouldThrow {
                        throw IOException()
                    }
                } catch(e: Throwable) {
                    throw AssertionError()
                }
            }
        }
    }

    Given("shouldThrow with a specified exception") {
        When("the block throws an exception of that type") {
            Then("it should not throw an assertion error") {
                try {
                    shouldThrow(IOException::class) {
                        throw IOException()
                    }
                    throw AssertionError()
                } catch(e: Throwable) {
                    // success
                }
            }
        }

        When("the block throws a different exception") {
            Then("it should throw an assertion error") {
                try {
                    shouldThrow(IOException::class) {
                        throw IllegalArgumentException()
                    }
                    throw AssertionError()
                } catch(e: Throwable) {
                    // success
                }
            }
        }

        When("the block does not throw an exception") {
            Then("it should throw an assertion error") {
                try {
                    shouldThrow(IOException::class) {
                        // do nothing
                    }
                    throw AssertionError()
                } catch(e: Throwable) {
                    // success
                }
            }
        }
    }
})
