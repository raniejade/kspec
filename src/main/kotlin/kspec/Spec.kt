package kspec

import kotlin.reflect.KClass

interface Spec {
    fun group(description: String, term: String? = null, block: () -> Unit)
    fun <T: Any> group(subject: KClass<T>, description: String = "", term: String? = null, block: SubjectSpec<T>.() -> Unit)
    fun example(description: String, term: String? = null, block: () -> Unit)
    fun before(action: () -> Unit)
    fun after(action: () -> Unit)
    fun beforeEach(action: () -> Unit)
    fun afterEach(action: () -> Unit)
}

interface SubjectSpec<T: Any>: Spec {
    val subject: T
    fun subject(block: () -> T)
    fun behavesLike(sharedExample: SharedExample<in T>)
}

interface SharedExample<T: Any> {
    fun example(): SubjectSpec<out T>.() -> Unit
}

fun <T: Any> sharedExample(block: SubjectSpec<out T>.() -> Unit): SharedExample<T> {
    return object: SharedExample<T> {
        override fun example(): SubjectSpec<out T>.() -> Unit = block
    }
}

fun Spec.describe(description: String, action: () -> Unit) {
    group(description, "describe", action)
}

fun <T: Any> Spec.describe(subject: KClass<T>, description: String = "", action: SubjectSpec<T>.() -> Unit) {
    group(subject, description, "describe", action)
}

fun Spec.context(description: String, action: () -> Unit) {
    group(description, "context", action)
}

fun <T: Any> Spec.context(subject: KClass<T>, description: String = "", action: SubjectSpec<T>.() -> Unit) {
    group(subject, description, "describe", action)
}

fun Spec.it(description: String, action: () -> Unit) {
    example(description, "it", action)
}

