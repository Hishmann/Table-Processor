package Modules.TableFunctions.TableModifier

import Models.Table.Table

/**
 * A trait for modifying a `Table` based on a specific context.
 *
 * This trait defines a contract for performing modifications on a `Table` object
 * using a context of type `T`. The modifications could include adding rows,
 * removing columns, or other transformations.
 *
 * @tparam T The type of the context used for the modification.
 */
trait TableModifier[T] {

    /**
     * Modifies the given `Table` using the specified context.
     *
     * @param input The `Table` object to be modified.
     * @param context An instance of type `T` that provides the context or parameters
     *                required for the modification.
     * @return A new `Table` object resulting from the modification.
     */
    def modify(input: Table, context: T): Table
}
