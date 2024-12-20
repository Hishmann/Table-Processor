package Modules.TableFunctions

import Models.Table.Cells.Cell
import Models.Table.Table

/**
 * An object that provides utilities for converting between column names and indices,
 * and for referencing table cells using standard spreadsheet-like notation.
 */
object ReferencingIndices {

    /**
     * Converts a column name (e.g., "A", "B", "AA") into a zero-based index.
     *
     * @param column A string representing the column name (case-insensitive).
     * @return An integer representing the zero-based index of the column.
     */
    def columnToIndex(column: String): Int = {
        column.toUpperCase.foldLeft(0) { (acc, char) =>
            acc * 26 + (char - 'A') // Calculate the index based on character positions
        }
    }

    /**
     * Converts a zero-based column index into a column name (e.g., 0 -> "A", 1 -> "B").
     *
     * @param index An integer representing the zero-based column index.
     * @return A string representing the column name in spreadsheet notation.
     */
    def indexToColumn(index: Int): String = {
        @annotation.tailrec
        def loop(idx: Int, acc: String): String = {
            if (idx < 0) acc // Base case: return accumulated column name
            else {
                // Calculate the current character and recurse for the next position
                loop((idx / 26) - 1, (('A' + (idx % 26)).toChar + acc))
            }
        }
        loop(index, "") // Start recursion with the given index and an empty string
    }

    /**
     * Retrieves a cell from a table based on a spreadsheet-like reference (e.g., "A1", "B2").
     *
     * @param ind A string representing the cell reference in "ColumnRow" format (e.g., "A1").
     * @param table The `Table` object from which to retrieve the cell.
     * @return The cell at the specified location in the table.
     * @throws RuntimeException if the cell reference format is invalid.
     */
    def tableIndexReturn(ind: String, table: Table): Cell[_] = {
        // Regular expression to match column letters and row digits in the cell reference
        val cellRefRegex = """([A-Z]+)(\d+)""".r

        // Extract column and row components from the reference string
        val (column, row) = ind match {
            case cellRefRegex(letters, digits) => (letters, digits)
            case _ => throw new RuntimeException(s"Invalid cell reference: $ind")
        }

        // Convert column letters to a zero-based index
        val col: Int = column.foldLeft(0) { (result, char) =>
            result * 26 + (char - 'A')
        }

        // Retrieve and return the cell from the table's data
        table.getData(row.toInt - 1)(col)
    }
}
