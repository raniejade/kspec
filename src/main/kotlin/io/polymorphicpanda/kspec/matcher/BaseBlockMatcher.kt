package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
open class BaseBlockMatcher(val clazz: Class<out Throwable>?): BlockMatcher() {
    override fun match(arg: (() -> Unit)?) {
        try {
            arg!!()
            noExceptionThrown(clazz)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            exceptionThrown(e, clazz)
        }
    }

    protected open fun exceptionThrown(throwable: Throwable, clazz: Class<out Throwable>?) {}
    protected open fun noExceptionThrown(clazz: Class<out Throwable>?) {}
}
