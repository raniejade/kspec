package io.polymorphicpanda.kspec.samples

open class Calculator {
    fun add(a: Int, b: Int): Int = a + b
    fun minus(a: Int, b: Int): Int = a - b
    fun multiply(a: Int, b: Int): Int = a * b
    fun divide(a: Int, b: Int): Int = a / b
}

class AdvancedCalculator: Calculator() {
    fun pow(a: Double, b: Double): Double = Math.pow(a, b)
    fun sqrt(a: Double): Double = TODO()
}
