package io.polymorphicpanda.speck.dsl

interface DataDriven {
    fun <P1> Given(description: String, given: Given.(P1) -> Unit): Where1<P1>
    fun <P1, P2> Given(description: String, given: Given.(P1, P2) -> Unit): Where2<P1, P2>
    fun <P1, P2, P3> Given(description: String, given: Given.(P1, P2, P3) -> Unit): Where3<P1, P2, P3>
}

interface WhereSpec1<P1> {
    fun row(p1: P1)
}

interface WhereSpec2<P1, P2> {
    fun row(p1: P1, p2: P2)
}

interface WhereSpec3<P1, P2, P3> {
    fun row(p1: P1, p2: P2, p3: P3)
}

interface Where1<P1> {
    fun Where(init: WhereSpec1<P1>.() -> Unit)
}

interface Where2<P1, P2> {
    fun Where(init: WhereSpec2<P1, P2>.() -> Unit)
}

interface Where3<P1, P2, P3> {
    fun Where(init: WhereSpec3<P1, P2, P3>.() -> Unit)
}
