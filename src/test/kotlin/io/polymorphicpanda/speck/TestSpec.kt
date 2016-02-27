package io.polymorphicpanda.speck

class TestSpec: Speck({
    Given("a foo") {
        BeforeWhen {
            println("before when $it")
        }

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

        AfterWhen {
            println("after when $it")
        }
    }
})
