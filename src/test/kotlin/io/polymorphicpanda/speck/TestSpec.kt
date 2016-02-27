package io.polymorphicpanda.speck

/**
 * @author Ranie Jade Ramiso
 */
class TestSpec: Speck({
    Given("a foo") {
        var i = 0
        When("the bar is full") {
            Then("foobar") {
                println(i)
            }

            Then("shit") {
                println(i)
            }
        }

        When("the bar is not full") {
            Then("foobar") {
                println(i)
            }

            Then("shit") {
                println(i)
            }
        }
    }
})
