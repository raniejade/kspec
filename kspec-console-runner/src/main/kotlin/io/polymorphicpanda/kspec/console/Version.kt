package io.polymorphicpanda.kspec.console

import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
object Version {
    private val bundle = ResourceBundle.getBundle("version", Locale.ENGLISH)

    val KOTLIN_VERSION = bundle.getString("kotlin.version")
    val KSPEC_VERSION = bundle.getString("kspec.version")
}
