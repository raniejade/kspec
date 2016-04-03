# KSpec 
[![Build Status](https://travis-ci.org/raniejade/kspec.svg?branch=master)](https://travis-ci.org/raniejade/kspec) [![codecov.io](https://codecov.io/github/raniejade/kspec/coverage.svg?branch=master)](https://codecov.io/github/raniejade/kspec?branch=master)

Specifications for Kotlin.

## Basic Structure
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

## Context
KSpec is heavily inspired by [RSpec](http://rspec.info/), `Context` is synonymous to RSpec's scopes.

### Example
The test method in JUnit, and created using `it`.

### ExampleGroup
Groups similar examples together (they might be testing the same `Subject` - more on this later) and is created by using `describe` or `context`. Be careful when placing logic in those They are eagerly evaluated and not a good place to put some initializing logic (use the provided fixtures).

## Fixtures
KSpec provides `before`, `beforeEach`, `afterEach` and `after` callbacks for each context.

## Subject
Just like RSpec, KSpec also support subjects! Using the example above, lets modify it to use the subject feature.
```kotlin
class TheMostAmazingAnimalSpec: KSpec() {
    override fun spec() {
        describe("the most amazing animal in the universe") {
            subject {
                return@subject GetMostAmazingAnimal();
            }
            
            it("should be a panda") {
                assertThat(subject.type, equalTo("panda"))
            }
            
            context("not a panda") {
                it("nope, not going to accept it") {
                    assertThat({
                        assertThat(subject.type, not(equalTo("panda")))
                    }, thrown(EndOfTheWorldException::class))
                }
            }
        }
    }
}
```

## Shared Examples
Sometimes it's convenient to reuse examples - like when testing for a subclass of something.
```kotlin
class CalculatorSpec: KSpec() {
    override fun spec() {
        describe(Calculator::class) {
            itBehavesLike(calculator())
        }
    }
    
    companion object {
        fun calculator() = sharedExample<Calculator> {
            describe("add") {
                it("...") {
                }
            }
            ...
        }
    }
}

class AdvancedCalculatorSpec: KSpec() {
    override fun spec() {
        describe(AdvancedCalculator::class) {
            itBehavesLike(CalculatorSpec.calculator())
        }
    }
}
```

## Runner
Currently only a JUnit 4 Runner is provided, make sure to annotate your class with `@RunWith(JUnitKSpecRunner)`.

## Maven Coordinates
Core library: `io.polymorphicpanda.kspec:kspec:<kspec-version>`.   
JUnit 4 Runner: `io.polymorphicpanda.kspec:kspec-junit-runner:<kspec-version>`
