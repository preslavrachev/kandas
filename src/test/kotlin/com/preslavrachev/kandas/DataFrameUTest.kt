package com.preslavrachev.kandas

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsAll
import assertk.assertions.containsOnly
import assertk.assertions.isNotNull
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DataFrameUTest {

    @Test
    fun `ensure column names when providing a list of maps`() {
        val df = DataFrame(listOf(
                mapOf(
                        "a" to 1,
                        "b" to 2
                ),
                mapOf(
                        "c" to 3,
                        "d" to 4
                )
        ))

        assertThat(df.columns)
                .isNotNull()
                .containsOnly("a", "b", "c", "d")
    }

    @Test
    fun `ensure that a single series contains the right items`() {
        val df = DataFrame(listOf(
                mapOf(
                        "a" to 1,
                        "b" to 2
                ),
                mapOf(
                        "c" to 3,
                        "d" to 4
                )
        ))

        assertThat(df["a"].toList())
                .isNotNull()
                .containsOnly(1, null)
    }
}
