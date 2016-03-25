# KSpec
[![Build Status](https://travis-ci.org/raniejade/kspec.svg?branch=master)](https://travis-ci.org/raniejade/kspec) [![codecov.io](https://codecov.io/github/raniejade/kspec/coverage.svg?branch=master)](https://codecov.io/github/raniejade/kspec?branch=master)

Specifications for Kotlin.

```kotlin
class TheMostAmazingAnimalSpec: KSpec() {
    override fun spec() {
        describe("the most amazing animal in the universe") {
            val animal = GetMostAmazingAnimal()
            it("should be a panda") {
                assertThat(animal.type, equalTo("panda"))
            }
            
            context("not a panda") {
                it("nope, not going to accept it") {
                    assertThat({
                        assertThat(animal.type, not(equalTo("panda")))
                    }, thrown(EndOfTheWorldException::class))
                }
            }
        }
    }
}
```

#### Samples
See tests for samples.
