package io.polymorphicpanda.speck.core

import io.polymorphicpanda.speck.dsl.*

internal abstract class DataEngine: DataDriven, Collector<Given>() {

    override fun <P1> Given(description: String, given: Given.(P1) -> Unit): Where1<P1> {
        return object: Where1<P1> {
            override fun Where(init: WhereSpec1<P1>.() -> Unit) {
                with(object: WhereSpec1<P1> {
                    override fun row(p1: P1) {
                        collect("$description [$p1]", {
                            given(this, p1)
                        })
                    }

                }, init)
            }


        }
    }

    override fun <P1, P2> Given(description: String, given: Given.(P1, P2) -> Unit): Where2<P1, P2> {
        return object: Where2<P1, P2> {
            override fun Where(init: WhereSpec2<P1, P2>.() -> Unit) {
                with(object: WhereSpec2<P1, P2> {
                    override fun row(p1: P1, p2: P2) {
                        collect("$description [$p1, $p2]", {
                            given(this, p1, p2)
                        })
                    }

                }, init)
            }
        }
    }

    override fun <P1, P2, P3> Given(description: String, given: Given.(P1, P2, P3) -> Unit): Where3<P1, P2, P3> {
        return object: Where3<P1, P2, P3> {
            override fun Where(init: WhereSpec3<P1, P2, P3>.() -> Unit) {
                with(object: WhereSpec3<P1, P2, P3> {
                    override fun row(p1: P1, p2: P2, p3: P3) {
                        collect("$description [$p1, $p2, $p3]", {
                            given(this, p1, p2, p3)
                        })
                    }

                }, init)
            }
        }
    }
}
