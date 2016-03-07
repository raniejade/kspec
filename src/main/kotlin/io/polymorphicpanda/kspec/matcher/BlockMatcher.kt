package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
abstract class BlockMatcher(val clazz: Class<out Throwable>?): AbstractMatcher<() -> Unit>(null) {
    override fun match(arg: () -> Unit) {
        var thrown = false
        try {
            arg()
        } catch (e: Throwable) {
            thrown = true
            exceptionThrown(e, clazz)
        }

        if (!thrown) {
            noExceptionThrown(clazz)
        }
    }

    protected abstract fun exceptionThrown(throwable: Throwable, clazz: Class<out Throwable>?)
    protected abstract fun noExceptionThrown(clazz: Class<out Throwable>?)
}
