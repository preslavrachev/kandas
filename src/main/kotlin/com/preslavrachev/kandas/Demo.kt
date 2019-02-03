package com.preslavrachev.kandas

fun main(args: Array<String>) {
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

    print(df.columnNames)
}
