# speck
[![Build Status](https://travis-ci.org/raniejade/speck.svg?branch=master)](https://travis-ci.org/raniejade/speck) [![codecov.io](https://codecov.io/github/raniejade/speck/coverage.svg?branch=master)](https://codecov.io/github/raniejade/speck?branch=master)

Born out of frustration of Spek and the love for Spock, a simple yet versatile testing framework for Kotlin.


#### Sample
```kotlin
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

    Given("a number, a string and a nullable") { 
        When("i do something") { a: Int, b: String, c: Any? ->
            Then("it will happen") {
            }
        }.Where {
            row(1, "foo", null)
            row(2, "bar", null)
            row(3, "haha", null)
            row(4, "shit", null)
        }
    }
})
```

#### Human readable failure report
```
java.lang.AssertionError: 

     Given two numbers a and b
       When c = b * a and d = a * b [1, 2]
         Then c == d
           * shouldBeEqual(2, 2)
           * shouldBeTrue(true)
           * shouldBeFalse(true} // assertion failed here

```
