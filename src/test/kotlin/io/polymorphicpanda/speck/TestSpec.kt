package io.polymorphicpanda.speck

class TestSpec: Speck({
    Given("a foo") {
        BeforeWhen {
            println("before when $it")
        }

        When("the bar is full") {
            Then("foobar") {
                shouldBeEqual(1, 1)
                shouldNotBeEqual(1, 2)
                shouldBeTrue(true)
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

    Given("a number, a string and a nullable") { a: Int, b: String, c: Any? ->
        When("i do something") {
            Then("it will happen") {
            }
        }
    }.Where {
        row(1, "foo", null)
        row(2, "bar", null)
        row(3, "haha", null)
        row(4, "shit", null)
    }
})
