package io.polymorphicpanda.speck

import io.polymorphicpanda.speck.data.DataDriver
import io.polymorphicpanda.speck.data.Where1
import io.polymorphicpanda.speck.data.Where2
import io.polymorphicpanda.speck.data.Where3
import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.When


fun <P1> Given.When(description: String, clause: When.(P1) -> Unit): Where1<P1> {
    return DataDriver.When(this, description, clause)
}

fun <P1, P2> Given.When(description: String, clause: When.(P1, P2) -> Unit): Where2<P1, P2> {
    return DataDriver.When(this, description, clause)
}

fun <P1, P2, P3> Given.When(description: String, clause: When.(P1, P2, P3) -> Unit): Where3<P1, P2, P3> {
    return DataDriver.When(this, description, clause)
}
