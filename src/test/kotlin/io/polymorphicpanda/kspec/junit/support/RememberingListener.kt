package io.polymorphicpanda.kspec.junit.support

import org.junit.runner.Description
import org.junit.runner.notification.Failure
import org.junit.runner.notification.RunListener
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class RememberingListener: RunListener() {
    val started = HashSet<String>()
    val finished = HashSet<String>()
    val failed = HashSet<String>()
    val ignored = HashSet<String>()

    override fun testStarted(description: Description?) {
        started.add(description!!.displayName)
    }

    override fun testFinished(description: Description?) {
        finished.add(description!!.displayName)
    }

    override fun testFailure(failure: Failure?) {
        failed.add(failure!!.description.displayName)
    }

    override fun testIgnored(description: Description?) {
        ignored.add(description!!.displayName)
    }

    fun isStarted(description: String) = started.contains(description)
    fun isFinished(description: String) = finished.contains(description)
    fun hasFailed(description: String) = failed.contains(description)
    fun isIgnored(description: String) = ignored.contains(description)
}
