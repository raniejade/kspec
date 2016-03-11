# KSpec
[![Build Status](https://travis-ci.org/raniejade/kspec.svg?branch=master)](https://travis-ci.org/raniejade/kspec) [![codecov.io](https://codecov.io/github/raniejade/kspec/coverage.svg?branch=master)](https://codecov.io/github/raniejade/kspec?branch=master)

Inspired by https://github.com/Quick/Quick/.

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

#### Grouping
Tests can be grouped using `describe` or `context`. There is no distinction between them, they are interchangeable and purely informational.

#### Fixtures
KSpec supports `before`, `after`, `beforeEach` and `afterEach`. 

#### Assertion Framework
Choice is up to you, KSpec does not really care. Possible options are:

- [kotlin.test](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.test/) package under Kotlin's stdlib.
- [HamKrest](https://github.com/npryce/hamkrest) - reimplementation of Hamcrest in Kotlin.

#### Architecture
TODO: talk about collection and execution phase, contexts, etc ...
