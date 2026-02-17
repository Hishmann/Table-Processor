package Modules.TableFunctions.TableCreator

import Models.Table.Table
import Models.Table.TextTable
import Models.Table.Cells.{Cell, EmptyCell, FormulaCell, IntCell}

/**
 * A class that implements `TableCreator` to create a `Table` from a `TextTable`.
 *
 * This class processes the rows and columns of a `TextTable` to construct a `Table` object,
 * where each cell is represented by a specific `Cell` type (e.g., `IntCell`, `FormulaCell`, `EmptyCell`).
 */
class TableCreatorFromText extends TableCreator[TextTable] {

    /**
     * Converts a `TextTable` into a `Table` object.
     *
     * @param input The `TextTable` containing the input data as a two-dimensional list of strings.
     * @return A `Table` object representing the structured data.
     * @throws RuntimeException if the input `TextTable` has rows of unequal length.
     */
    def getTable(input: TextTable): Table = {
        val inputData = input.data // Extract the raw data from the `TextTable`
        var formula: String = ""  // Placeholder for formula cells
        var result: List[List[Cell[_]]] = List() // Initialize the table data structure
        var resultTable: Table = Table() // Create an empty `Table` object

        // If the input data is empty, return the empty `Table`
        if (inputData.isEmpty) {
            return resultTable
        }

        // Validate that all rows in the input data have the same length
        for (i <- 0 to inputData.length - 2) {
            if (inputData(i).length != inputData(i + 1).length) {
                throw new RuntimeException("The CSV format is wrong")
            }
        }

        // Set the rows and columns for the result `Table`
        resultTable.setRows(inputData.indices.toList)
        resultTable.setColumns(inputData.head.indices.toList)

        // Iterate through the rows and columns of the input data to construct cells
        for (i <- inputData.indices.toList) {
            var innerRow: List[Cell[_]] = List() // Initialize a new row for the result table
            for (j <- inputData(i).indices.toList) {
                formula = inputData(i)(j) // Get the current cell content
                var temp: Cell[_] = new EmptyCell() // Default to `EmptyCell`

                // Determine the type of cell to create based on the content
                if (!formula.forall(_.isDigit) && formula.nonEmpty) {
                    // If the content is not numeric and not empty, treat it as a formula
                    temp = new FormulaCell(formula)
                } else if (formula.nonEmpty) {
                    // If the content is numeric, create an `IntCell`
                    temp = new IntCell(formula.toInt)
                }

                // Add the cell to the current row
                innerRow = innerRow :+ temp
            }
            // Add the completed row to the result table data
            result = result :+ innerRow
        }

        // Set the constructed data into the result `Table`
        resultTable.setData(result)
        resultTable
    }
}



