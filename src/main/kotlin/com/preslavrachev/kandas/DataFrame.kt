package com.preslavrachev.kandas

import com.preslavrachev.kandas.core.input.InputData
import com.preslavrachev.kandas.core.input.KandasDataProvider
import com.preslavrachev.kandas.core.input.ListInput
import koma.extensions.get
import koma.ndarray.NDArray


class Series<T>(val data: NDArray<T>) : NDArray<T> by data {
    override fun toString(): String {
        val rowIdx = 0 until data.shape()[0]
        val colIdx = 0 until data.shape()[1]

        val buider = StringBuilder()

        rowIdx.forEach { row ->
            buider.append(row)

            colIdx.forEach { col ->
                buider.append("\t")
                buider.append(data[row, col])
            }

            buider.append("\n")
        }

        return buider.toString()
    }
}

class DataFrame(val data: InputData) : KandasDataProvider by data {

    private val invertedColumnIdx: Map<String, Int> = columnNames.toList()
            .zip(0 until columnNames.size)
            .toMap()

    constructor(data: List<Map<String, Any>>) : this(ListInput(data))

    operator fun get(column: String): Series<out Any> {
        // TODO: Avoid having to loop every time
        val key = columns.keys.filter { it.name == column }.first()
        return Series(columns[key]!!)
    }

}
