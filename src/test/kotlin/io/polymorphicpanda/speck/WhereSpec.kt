package io.polymorphicpanda.speck

/**
 * @author Ranie Jade Ramiso
 */
class WhereSpec: Speck({
    Given("any number") {
        When("multiplied with 1") { i: Int ->
            Then("result should be the same number") {
                shouldBeEqual(i, i * 1)
            }
        }.Where {
            row(1)
            row(5)
            row(512)
            row(125)
        }
    }

    Given("two numbers a and b") {
        When("c = b * a and d = a * b") { a: Int, b: Int ->
            Then("c == d") {
                shouldBeEqual(a * b, b * a)
            }
        }.Where {
            row(1, 2)
            row(3, 6)
            row(512, 612)
            row(6123, 12)
        }
    }

    Given("three numbers a, b and c") {
        When("a is multiplied by b") { a: Int, b: Int, c: Int ->
            Then("product is c") {

            }
        }.Where {
            row(1, 1, 1)
            row(2, 1, 2)
            row(2, 3, 6)
            row(4, 3, 12)
        }
    }
})
