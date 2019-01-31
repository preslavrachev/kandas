package com.preslavrachev.kandas.core

/**
 * Canonical n-dimensional unit of homogeneous dtype contained in a pandas
 * data structure
 *
 * Index-ignorant; let the container take care of that
 */
class Block(val values: Array<Any>): KandasObject() {

}

/**
 * Core internal data structure to implement DataFrame, Series, Panel, etc.
 *
 * Manage a bunch of labeled 2D mixed-type ndarrays. Essentially it's a
 * lightweight blocked set of labeled data to be manipulated by the DataFrame
 * public API class
 */
class BlockManager(val axes:Array<Any>,
                   val blocks: Array<Block>): KandasObject() {
}
