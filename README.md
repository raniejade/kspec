# speck
[![Build Status](https://travis-ci.org/raniejade/speck.svg?branch=master)](https://travis-ci.org/raniejade/speck) [![codecov.io](https://codecov.io/github/raniejade/speck/coverage.svg?branch=master)](https://codecov.io/github/raniejade/speck?branch=master)

Born out of frustration of Spek and the love for Spock, a simple yet versatile testing framework for Kotlin.


#### Sample
```
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

```

Output
```
before when the bar is full
after when the bar is full
before when the bar is not full
after when the bar is not full
```
