package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
class Throw(clazz: Class<out Throwable>?): BaseBlockMatcher(clazz) {
    override fun exceptionThrown(throwable: Throwable, clazz: Class<out Throwable>?) {
        if (clazz != null && !clazz.isInstance(throwable)) {
            fail("expected $clazz but '${throwable.javaClass}' was thrown")
        }
    }

    override fun noExceptionThrown(clazz: Class<out Throwable>?) {
        if (clazz == null) {
            fail("exception was expected but non was thrown")
        } else {
            fail("expected $clazz but non was thrown")
        }
    }
}
