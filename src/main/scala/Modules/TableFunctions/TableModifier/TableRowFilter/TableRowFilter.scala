package Modules.TableFunctions.TableModifier.TableRowFilter

import Models.FilterModels.Table.TableRowFilterContext
import Models.Table.Table
import Modules.TableFunctions.TableModifier.TableModifier
import Modules.TableFunctions.ReferencingIndices.columnToIndex

/**
 * A class that implements `TableModifier` to filter rows in a `Table` based on a specified column and filter criteria.
 *
 * The `TableRowFilter` class removes rows from a `Table` where a specified filter condition is met
 * on the values of a specific column.
 */
class TableRowFilter extends TableModifier[TableRowFilterContext] {

    /**
     * Modifies a `Table` by removing rows that meet the filter criteria in a specified column.
     *
     * @param table The input `Table` to be modified.
     * @param context A `TableRowFilterContext` specifying the column and filter criteria.
     * @return A new `Table` object with the filtered rows removed.
     */
    def modify(table: Table, context: TableRowFilterContext): Table = {
        val res = new Table() // Create a new empty table for the result

        var rowsToRemove: List[Int] = List() // Initialize a list of rows to be removed
        val col = context.column // Get the column name from the context
        val filter = context.filter // Get the filter from the context

        val columnPattern = "^[A-Z]+$".r
        if (!columnPattern.matches(col)) throw new RuntimeException("Invalid column format in table filter context")
        val columnVal = columnToIndex(col) // Convert the column name to a zero-based index

        // Iterate through all rows in the table
        for (i <- table.getRows) {
            // Check if the filter condition is met for the cell in the specified column
            if (filter.doFilter(table.getData(i)(columnVal))) {
                if (!rowsToRemove.contains(i)) {
                    rowsToRemove = rowsToRemove :+ i // Add the row to the list of rows to remove
                }
            }
        }

        // Set the data of the resulting table (unchanged from the original table)
        res.setData(table.getData)

        // Set the rows of the resulting table, excluding the rows to be removed
        res.setRows(table.getRows.diff(rowsToRemove))

        res // Return the modified table
    }
}
