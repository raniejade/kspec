package io.polymorphicpanda.speck

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
})
