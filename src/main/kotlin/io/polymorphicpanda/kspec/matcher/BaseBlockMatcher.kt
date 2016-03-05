package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
open class BaseBlockMatcher(val clazz: Class<out Throwable>?): BlockMatcher() {
    override fun match(arg: (() -> Unit)?) {
        var thrown = false
        try {
            arg!!()
        } catch (e: Throwable) {
            thrown = true
            exceptionThrown(e, clazz)
        }

        if (!thrown) {
            noExceptionThrown(clazz)
        }
    }

    protected open fun exceptionThrown(throwable: Throwable, clazz: Class<out Throwable>?) {
    }

    protected open fun noExceptionThrown(clazz: Class<out Throwable>?) {
    }
}
