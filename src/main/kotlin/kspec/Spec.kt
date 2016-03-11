package kspec

interface Spec {
    fun specBlock(description: String, term: String? = null, terminal: Boolean = false, block: () -> Unit)
    fun before(action: () -> Unit)
    fun after(action: () -> Unit)
    fun beforeEach(action: () -> Unit)
    fun afterEach(action: () -> Unit)
}

fun Spec.describe(description: String, action: () -> Unit) {
    specBlock(description, "describe", false, action)
}

fun Spec.context(description: String, action: () -> Unit) {
    specBlock(description, "context", false, action)
}

fun Spec.it(description: String, action: () -> Unit) {
    specBlock(description, "it", true, action)
}

