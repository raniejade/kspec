package kspec

import kotlin.reflect.KClass

fun Spec.describe(description: String, action: () -> Unit) {
    group("describe: $description", action)
}

fun <T: Any> Spec.describe(subject: KClass<T>, description: String = "%s", action: SubjectSpec<T>.() -> Unit) {
    group(subject, "describe: $description", action)
}

fun Spec.context(description: String, action: () -> Unit) {
    group("context: $description", action)
}

fun <T: Any> Spec.context(subject: KClass<T>, description: String = "%s", action: SubjectSpec<T>.() -> Unit) {
    group(subject, "context: $description", action)
}

fun Spec.it(description: String, action: () -> Unit) {
    example("it: $description", action)
}

fun Spec.xit(description: String, reason: String? = null, block: (() -> Unit)? = null) {
    pendingExample("it: $description", reason, block)
}

fun <T: Any> SubjectSpec<T>.itBehavesLike(sharedExample: SharedExample<in T>) {
    include(sharedExample)
}

