package io.polymorphicpanda.speck.runner

import io.polymorphicpanda.speck.dsl.Given
import io.polymorphicpanda.speck.dsl.Then
import io.polymorphicpanda.speck.dsl.When
import org.junit.runner.Description

/**
 * @author Ranie Jade Ramiso
 */
internal data class Feature(val description: Description,
                            val triple: Triple<Given.() -> Unit, When.() -> Unit, Then.() -> Unit>)
