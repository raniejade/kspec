package io.polymorphicpanda.speck

/**
 * @author Ranie Jade Ramiso
 */
class TestSpec: Speck({
    Given("a foo") {
        When("the bar is full") {
            Then("foobar") {
            }

            Then("shit") {
            }
        }

        When("the bar is not full") {
            Then("foobar") {
            }

            Then("shit") {
            }
        }
    }
})
