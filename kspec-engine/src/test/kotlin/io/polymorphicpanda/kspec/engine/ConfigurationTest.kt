package io.polymorphicpanda.kspec.engine

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.sameInstance
import io.polymorphicpanda.kspec.Configuration
import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.annotation.Configurations
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.engine.discovery.DiscoveryRequest
import io.polymorphicpanda.kspec.engine.execution.ExecutionNotifier
import io.polymorphicpanda.kspec.engine.execution.ExecutionRequest
import io.polymorphicpanda.kspec.tag.SimpleTag
import org.junit.Ignore
import org.junit.Test
import java.util.*

/**
 * @author Ranie Jade Ramiso
 */
class ConfigurationTest {
    object Order: LinkedList<Any>()

    object Match: SimpleTag()
    object Exclude: SimpleTag()

    @Test
    fun testOrder() {
        val notifier = ExecutionNotifier()
        val engine = KSpecEngine(notifier)

        class SharedConfiguration1: Configuration {
            override fun apply(config: KSpecConfig) {
                Order.add(this)
            }
        }

        class SharedConfiguration2: Configuration {
            override fun apply(config: KSpecConfig) {
                Order.add(this)
            }
        }


        @Configurations(SharedConfiguration1::class, SharedConfiguration2::class)
        class TestSpec: KSpec() {
            override fun configure(config: KSpecConfig) {
                Order.add(this)
            }

            override fun spec() { }
        }

        val result = engine.discover(DiscoveryRequest(listOf(TestSpec::class), KSpecConfig()))

        engine.execute(result)

        assertThat(Order.size, equalTo(3))
        assertThat(Order[0] is SharedConfiguration1, equalTo(true))
        assertThat(Order[1] is SharedConfiguration2, equalTo(true))
        assertThat(Order[2] is TestSpec, equalTo(true))
    }

    @Test
    fun testInheritance() {
        val notifier = ExecutionNotifier()
        val engine = KSpecEngine(notifier)
        val config = KSpecConfig()
        config.filter.matching(Match::class)
        config.filter.exclude(Exclude::class)

        class TestSpec: KSpec() {
            lateinit var config: KSpecConfig

            override fun configure(config: KSpecConfig) {
                this.config = config
            }

            override fun spec() { }
        }

        val result = engine.discover(DiscoveryRequest(listOf(TestSpec::class), config))

        engine.execute(ExecutionRequest(result))

        val instance = result.instances.keys.first() as TestSpec

        assertThat(instance.config, !sameInstance(config))
        assertThat(instance.config.filter.match.contains(Match::class), equalTo(true))
        assertThat(instance.config.filter.excludes.contains(Exclude::class), equalTo(true))
    }
}
