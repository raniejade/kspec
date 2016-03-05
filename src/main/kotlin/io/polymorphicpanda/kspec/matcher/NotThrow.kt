package io.polymorphicpanda.kspec.matcher

/**
 * @author Ranie Jade Ramiso
 */
class NotThrow(clazz: Class<out Throwable>?): BaseBlockMatcher(clazz) {
    override fun exceptionThrown(throwable: Throwable, clazz: Class<out Throwable>?) {
        if (clazz != null && clazz == throwable.javaClass) {
            fail()
        }
    }
}
