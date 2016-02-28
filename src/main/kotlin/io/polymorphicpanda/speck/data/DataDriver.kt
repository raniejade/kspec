package io.polymorphicpanda.speck.data

import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.When


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

internal object DataDriver {
    fun <P1> When(spec: Given, description: String, clause: When.(P1) -> Unit): Where1<P1> {
        return object: Where1<P1> {
            override fun Where(init: WhereSpec1<P1>.() -> Unit) {
                with(object: WhereSpec1<P1> {
                    override fun row(p1: P1) {
                        spec.When("$description [$p1]", {
                            clause(this, p1)
                        })
                    }

                }, init)
            }


        }
    }

    fun <P1, P2> When(spec: Given, description: String, clause: When.(P1, P2) -> Unit): Where2<P1, P2> {
        return object: Where2<P1, P2> {
            override fun Where(init: WhereSpec2<P1, P2>.() -> Unit) {
                with(object: WhereSpec2<P1, P2> {
                    override fun row(p1: P1, p2: P2) {
                        spec.When("$description [$p1, $p2]", {
                            clause(this, p1, p2)
                        })
                    }

                }, init)
            }
        }
    }

    fun <P1, P2, P3> When(spec: Given, description: String, clause: When.(P1, P2, P3) -> Unit): Where3<P1, P2, P3> {
        return object: Where3<P1, P2, P3> {
            override fun Where(init: WhereSpec3<P1, P2, P3>.() -> Unit) {
                with(object: WhereSpec3<P1, P2, P3> {
                    override fun row(p1: P1, p2: P2, p3: P3) {
                        spec.When("$description [$p1, $p2, $p3]", {
                            clause(this, p1, p2, p3)
                        })
                    }

                }, init)
            }
        }
    }
}
