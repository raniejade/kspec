package io.polymorphicpanda.speck

/**
 * @author Ranie Jade Ramiso
 */
class TestSpec: Speck({
    Given("a foo") {
        When("the bar") {
            Then("foobar") {
            }
        }
    }

    Given("another foo") {
        When("the another bar") {
            Then("another foobar") {
            }
        }
    }
})
