package io.polymorphicpanda.kspec.engine.discovery

import io.polymorphicpanda.kspec.KSpec
import io.polymorphicpanda.kspec.config.KSpecConfig
import io.polymorphicpanda.kspec.engine.query.Query
import kotlin.reflect.KClass

/**
 * @author Ranie Jade Ramiso
 */
class DiscoveryRequest(val specs: List<KClass<out KSpec>>,
                       val config: KSpecConfig,
                       val query: Query? = null)
