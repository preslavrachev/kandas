package com.preslavrachev.kandas

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isNotNull
import com.karumi.kotlinsnapshot.matchWithSnapshot
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

        assertThat(df.columnNames)
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

        print(df["a"].toList())

        assertThat(df["a"].toList())
                .isNotNull()
                .containsOnly(1.0, Double.NaN)
    }

    @Test
    fun `ensure that the printed output of a data frame matches that of an equivalent Pandas df`() {
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

        df.toString().matchWithSnapshot("DataFrame output")
    }
}
