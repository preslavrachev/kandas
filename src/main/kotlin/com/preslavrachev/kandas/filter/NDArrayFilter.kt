package com.preslavrachev.kandas.filter

import koma.ndarray.NDArray

infix fun <T : Comparable<T>> NDArray<T>.gt(t: T): NDArray<Boolean> {
    return NDArray.createGeneric(shape()[0], shape()[1]) {
        getGeneric(it[0], it[1]) > t
    }
}
