package Modules.TableFunctions.TableCreator

import Models.Table.Table

/**
 * A trait for creating a `Table` from input data of a specified type.
 *
 * This trait defines a contract for transforming data of type `T` into a `Table` object.
 * It provides an abstraction for generating table structures from various types of input data.
 *
 * @tparam T The type of the input data used to create the table.
 */
trait TableCreator[T] {

    /**
     * Transforms the given input data into a `Table`.
     *
     * @param inputData The input data of type `T` used to generate the table.
     * @return A `Table` object created from the input data.
     */
    def getTable(inputData: T): Table
}
