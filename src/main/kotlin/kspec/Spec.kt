package kspec

interface Spec {
    fun describe(description: String, action: () -> Unit)
    fun context(description: String, action: () -> Unit)
    fun it(description: String, action: It.() -> Unit)
    fun before(action: () -> Unit)
    fun after(action: () -> Unit)
    fun beforeEach(action: () -> Unit)
    fun afterEach(action: () -> Unit)
}
