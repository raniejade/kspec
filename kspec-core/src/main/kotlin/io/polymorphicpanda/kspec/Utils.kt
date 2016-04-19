package io.polymorphicpanda.kspec

import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
object Utils {
    inline fun <reified T: Annotation> findAnnotation(clazz: KClass<*>, annotation: KClass<T>): T? {
        return clazz.annotations.filterIsInstance(annotation.java)
            .firstOrNull()
    }

    fun <T: Any> instantiateUsingNoArgConstructor(clazz: KClass<T>): T {
        return clazz.constructors.filter { it.parameters.isEmpty() }
            .map { it.call() }
            .first()
    }
}
