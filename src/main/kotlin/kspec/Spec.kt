package kspec

import kotlin.reflect.KClass

interface Spec {
    fun group(description: String, term: String? = null, block: () -> Unit)
    fun <T: Any> group(subject: KClass<T>, description: String, term: String? = null, block: SubjectSpec<T>.() -> Unit)
    fun example(description: String, term: String? = null, block: () -> Unit)
    fun before(action: () -> Unit)
    fun after(action: () -> Unit)
    fun beforeEach(action: () -> Unit)
    fun afterEach(action: () -> Unit)
}

interface SubjectSpec<T>: Spec {
    fun subject(block: () -> T)
    fun subject(): T
}

fun Spec.describe(description: String, action: () -> Unit) {
    group(description, "describe", action)
}

fun <T: Any> Spec.describe(subject: KClass<T>, description: String, action: SubjectSpec<T>.() -> Unit) {
    group(subject, description, "describe", action)
}

fun Spec.context(description: String, action: () -> Unit) {
    group(description, "context", action)
}

fun <T: Any> Spec.context(subject: KClass<T>, description: String, action: SubjectSpec<T>.() -> Unit) {
    group(subject, description, "describe", action)
}

fun Spec.it(description: String, action: () -> Unit) {
    example(description, "it", action)
}

