package com.preslavrachev.kandas

import com.preslavrachev.kandas.filter.gt
import koma.extensions.get
import koma.ndarray.NDArray

//sealed class CellType
//data class IntCellType(val intVal: Int): CellType()

interface KandasDataProvider {
    val columns: Set<String>
    //val shape: Array<Int>
    val values: NDArray<Int?>
}

sealed class InputData : KandasDataProvider
class ListInput(val data: List<Map<String, Int?>>) : InputData() {

    var _columns: Set<String>
    var _values: NDArray<Int?>

    private val columnIdx: Map<Int, String>


    init {
        _columns = data.asSequence()
                .flatMap { it.keys.asSequence() }
                .toSet()

        columnIdx = (0 until _columns.size).toList().zip(_columns).toMap()

        _values = NDArray.createGeneric(data.size, columns.size) { x ->
            data[x[0]][columnIdx[x[1]]]
        }
    }

    override val columns: Set<String>
        get() = _columns

    override val values: NDArray<Int?>
        get() = _values
}
//data class Map(val data: kotlin.collections.Map<String, Any>): InputData()





class Series<T: Comparable<T>>(val data: NDArray<T>) : NDArray<T> by data {
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

    infix fun gt(t: T): Series<Boolean> {
        return Series(data gt t)
    }
}

class DataFrame(val data: InputData) : KandasDataProvider by data {

    private val invertedColumnIdx: Map<String, Int> = columns.toList()
            .zip(0 until columns.size)
            .toMap()

    constructor(data: List<Map<String, Int?>>) : this(ListInput(data))

    operator fun get(column: String): Series<Int?> {
        return Series(
                values[(0 until values.shape()[0]),
                        (invertedColumnIdx[column]!! until (invertedColumnIdx[column]!!) + 1)])
    }

}
