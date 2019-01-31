package com.preslavrachev.kandas

//sealed class CellType
//data class IntCellType(val intVal: Int): CellType()

interface KandasDataProvider {
    val columns: Set<String>
}

sealed class InputData : KandasDataProvider
data class ListInput(val data: List<Map<String, Any>>) : InputData() {

    var _columns: Set<String> = setOf()

    init {
        _columns = data.asSequence()
                .flatMap { it.keys.asSequence() }
                .toSet()
    }

    override val columns: Set<String>
        get() = _columns
}
//data class Map(val data: kotlin.collections.Map<String, Any>): InputData()

class DataFrame(val data: InputData): KandasDataProvider by data {

    constructor(data: List<Map<String, Any>>) : this(ListInput(data))


    operator fun DataFrame.get(vararg columns: String) {

    }

}
