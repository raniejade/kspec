package kspec

interface Spec {
    fun group(description: String, term: String? = null, block: () -> Unit)
    fun example(description: String, term: String? = null, block: () -> Unit)
    fun before(action: () -> Unit)
    fun after(action: () -> Unit)
    fun beforeEach(action: () -> Unit)
    fun afterEach(action: () -> Unit)
}

fun Spec.describe(description: String, action: () -> Unit) {
    group(description, "describe", action)
}

fun Spec.context(description: String, action: () -> Unit) {
    group(description, "context", action)
}

fun Spec.it(description: String, action: () -> Unit) {
    example(description, "it", action)
}

