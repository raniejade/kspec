package io.polymorphicpanda.speck.core

import io.polymorphicpanda.speck.dsl.*

interface Clause {
    fun description(): String
}

internal abstract class AbstractClause<T: Clause, K: SpeckDsl>(val action: K.() -> Unit,
                                                  description: String, val parent: T?): Clause {

    private val _description by lazyOf(description)

    override fun description(): String = "${getPrefix()} $_description"

    abstract fun getPrefix(): String
}

internal abstract class CompoundClause<T: Clause, K: Clause, S: SpeckDsl>(action: S.() -> Unit,
                                                             description: String, parent: T?,
                                                             val children: MutableList<K> = mutableListOf())
    : AbstractClause<T, S>(action, description, parent) {

    protected var before: ((String) -> Unit)? = null
    protected var after: ((String) -> Unit)? = null

    protected lateinit var consumer: (K) -> Unit

    fun execute(consumer: (K) -> Unit) {
        this.consumer = consumer
        with(unwrap(), action)
    }

    protected fun notify(item: K) {
        consumer(item)
    }

    protected abstract fun unwrap(): S

    internal fun invokeBefore(description: String) {
        var parent = (parent as? CompoundClause<*, *, *>)
        parent?.invokeBefore(description)
        before?.invoke(description)
    }

    internal fun invokeAfter(description: String) {
        var parent = (parent as? CompoundClause<*, *, *>)
        after?.invoke(description)
        parent?.invokeAfter(description)
    }
}

internal abstract class TerminalClause<T: AbstractClause<*, *>, K: SpeckDsl>(action: K.() -> Unit,
                                                                description: String, parent: T?)
    : AbstractClause<T, K>(action, description, parent) {

    abstract fun execute()
}

internal class RootClause(action: Root.() -> Unit, description: String)
    : CompoundClause<Clause, GivenClause, Root>(action, description, null), Root {
    override fun unwrap(): Root = this

    override fun Given(description: String, clause: Given.() -> Unit) {
        notify(GivenClause(clause, description, this))
    }

    override fun getPrefix(): String = ""

}

internal class GivenClause(action: Given.() -> Unit, description: String, parent: RootClause)
    : CompoundClause<RootClause, AbstractClause<*, *>, Given>(action, description, parent), Given {

    override fun Before(action: (String) -> Unit) {
        before = action
    }

    override fun When(description: String, clause: When.() -> Unit) {
        notify(WhenClause(clause, description, this))
    }

    override fun After(action: (String) -> Unit) {
        after = action
    }

    override fun unwrap(): Given = this

    override fun getPrefix(): String = "Given"
}

internal class WhenClause(action: When.() -> Unit, description: String, parent: GivenClause)
    : CompoundClause<GivenClause, ThenClause, When>(action, description, parent), When {
    override fun Then(description: String, clause: Then.() -> Unit) {
        notify(ThenClause(clause, description, this))
    }

    override fun unwrap(): When = this
    override fun getPrefix(): String = "When"
}

internal class ThenClause(action: Then.() -> Unit, description: String, parent: CompoundClause<*, *, *>)
    : TerminalClause<CompoundClause<*, *, *>, Then>(action, description, parent) {
    override fun getPrefix(): String = "Then"

    override fun execute() {
        parent?.invokeBefore(description())
        with(Assertions(Feature(collect(this).reversed())), action)
        parent?.invokeAfter(description())
    }

    private fun <T: Clause> collect(clause: T?): List<String> {
        return when (clause) {
            null -> emptyList()
            is RootClause -> emptyList()
            is AbstractClause<*, *> -> mutableListOf(clause.description()) + collect(clause.parent)
            else -> listOf(clause.description())
        }
    }

}
