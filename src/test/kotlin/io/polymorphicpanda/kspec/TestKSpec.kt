package io.polymorphicpanda.kspec

import kspec.KSpec

/**
 * @author Ranie Jade Ramiso
 */
class TestKSpec: KSpec() {
    override fun spec() {
        before { println("before once") }
        beforeEach {
            println("before")
        }
        describe("a foo") {
            beforeEach {
                println("another before")
            }
            describe("another foo") {
                context("they are twins") {
                    it ("should be okay") {
                        println("yep it's okay")
                    }
                }
            }
            it ("should be a good foo") {
                println("wtf")
            }

            afterEach {
                println("another after")
            }
        }

        it ("nonetheless") {
            println("hahaha")
        }

        context("ha!") {
            describe("foooo!") {
                it ("damn!") {
                    println("wooot")
                }
            }

            it("whyy") {
                println("noooo")
            }
        }

        afterEach {
            println("after")
        }
    }
}
