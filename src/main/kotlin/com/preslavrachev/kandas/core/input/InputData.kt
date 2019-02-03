package com.preslavrachev.kandas.core.input

import koma.extensions.fill
import koma.ndarray.NDArray
import kotlin.reflect.KClass

data class NameAndDataTypeKey(val name: String, val dataType: Pair<KClass<*>, (Any?) -> Any>)

interface KandasDataProvider {
    val columns: Map<NameAndDataTypeKey, NDArray<out Any>>
    val columnNames: Set<String>
    //val shape: Array<Int>
    val values: NDArray<Any>
}

sealed class InputData : KandasDataProvider
//data class Map(val data: kotlin.collections.Map<String, Any>): InputData()

class ListInput(val data: List<Map<String, Any>>) : InputData() {

    private var _columns: Map<NameAndDataTypeKey, NDArray<out Any>>
    private var _columnNames: Set<String>
    private var _values: NDArray<Any>

    private val columnIdx: Map<Int, String>

    fun arrayOfZeros(size: Int, clazz: KClass<*>) = when (clazz) {
        Double::class -> NDArray.doubleFactory.zeros(1, size)
        else -> NDArray.createGeneric(1, size) { "" }
    }

    init {
        _columns = data.asSequence().flatMap { it.entries.asSequence() }
                .map {
                    val key = NameAndDataTypeKey(name = it.key, dataType = when (it.value::class) {
                        Double::class -> Pair(Double::class) { a -> a ?: Double.NaN }
                        Int::class -> Pair(Double::class) { a -> (a as Int?)?.toDouble() ?: Double.NaN }
                        // TODO: Add the rest
                        else -> Pair(Int::class, { a -> a.toString() })
                    })

                    Pair(key, arrayOfZeros(data.size, it.key::class))
                }.toMap()

        _columns.forEach { key, zeros ->
            when (key.dataType.first) {
                Double::class -> (zeros as NDArray<Double>).fill { dims -> key.dataType.second(data[dims[1]][key.name]) as Double }
                else -> (zeros as NDArray<String>).fill { dims -> key.dataType.second(data[dims[0]][key.name]) as String }
            }
        }

        _columnNames = data.asSequence()
                .flatMap { it.keys.asSequence() }
                .toSet()

        columnIdx = (0 until _columnNames.size).toList().zip(_columnNames).toMap()

        // TODO: Remove once the switch to
        // This is a temporary fix for ensuring that the rest of the code works as before.
        _values = NDArray.createGeneric(data.size, columnNames.size) { x ->
            (data[x[0]][columnIdx[x[1]]] as Int?)?.toDouble() ?: Double.NaN
        }
    }

    override val columns: Map<NameAndDataTypeKey, NDArray<out Any>>
        get() = _columns

    override val columnNames: Set<String>
        get() = _columnNames

    override val values: NDArray<Any>
        get() = _values
}
