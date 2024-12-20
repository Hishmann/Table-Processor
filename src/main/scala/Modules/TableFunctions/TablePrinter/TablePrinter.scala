package Modules.TableFunctions.TablePrinter

import Models.Table.Table

/**
 * A trait for printing or representing a `Table` in a specific format.
 *
 * This trait defines a contract for transforming a `Table` object into a representation
 * of type `T`, which could be a string, a file, or any other format.
 *
 * @tparam T The type of the result produced by the printing operation.
 */
trait TablePrinter[T] {

    /**
     * Converts the given `Table` into a representation of type `T`.
     *
     * @param table The `Table` object to be printed or represented.
     * @return The representation of the table in the specified format as an object of type `T`.
     */
    def print(table: Table): T
}
