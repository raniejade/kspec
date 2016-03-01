package io.polymorphicpanda.speck

/**
 * @author Ranie Jade Ramiso
 */
class BeforeAfterWhenSpec: Speck({
    Given("BeforeWhen") {
        var test = false

        BeforeWhen {
            test = true
        }

        When("BeforeWhen is invoked") {
            Then("test should be true") {
                shouldBeTrue(test)
            }
        }
    }

    Given("AfterWhen") {
        var test = false
        When("BeforeWhen is invoked") {
            Then("test should be false") {
                shouldBeFalse(test)
            }
        }

        When("AfterWhen is invoked") {
            Then("test should be false") {
                shouldBeTrue(test)
            }
        }


        AfterWhen {
            test = true
        }
    }
})
