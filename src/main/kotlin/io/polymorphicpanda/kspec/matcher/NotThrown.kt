package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
class NotThrown(clazz: Class<out Throwable>?): BlockMatcher(clazz) {
    override fun noExceptionThrown(clazz: Class<out Throwable>?) {
        // all good
    }

    override fun exceptionThrown(throwable: Throwable, clazz: Class<out Throwable>?) {
        if (clazz != null) {
            if (clazz == throwable.javaClass) {
                fail("${throwable.javaClass} is not expected, but was thrown")
            }
        } else {
            fail("no exception expected but ${throwable.javaClass} was thrown")
        }
    }
}
