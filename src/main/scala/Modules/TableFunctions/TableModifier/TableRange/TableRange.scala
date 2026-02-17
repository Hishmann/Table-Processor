package Modules.TableFunctions.TableModifier.TableRange

import Models.Table.Table
import Models.RangeModels.RangeContext
import Modules.TableFunctions.TableModifier.TableModifier
import Modules.TableFunctions.ReferencingIndices.columnToIndex

/**
 * A class that implements `TableModifier` to modify a `Table` based on a range context.
 *
 * The `TableRange` class extracts a subset of a `Table` defined by a range (start and stop cells).
 * The range is specified using spreadsheet-style references (e.g., "A1", "B2").
 *
 * @throws RuntimeException if the input range format is invalid or if the range start is greater than the range stop.
 */
class TableRange extends TableModifier[RangeContext] {

    /**
     * Compares two cells based on their row and column indices.
     *
     * @param r1 Row number of the first cell.
     * @param c1 Column number of the first cell.
     * @param r2 Row number of the second cell.
     * @param c2 Column number of the second cell.
     * @return `1` if the first cell is greater, `-1` if the second cell is greater, `0` if equal.
     */
    def compareCells(r1: Int, c1: Int, r2: Int, c2: Int): Int = {
        // Compare column numbers first
        if (c1 < c2 && r1 < r2) {
            1
        } else if (c1 > c2 && r1 > r2 ) {
            2
        } else if (c1 < c2 && r1 > r2 ) {
            3
        } else if (c1 > c2 && r1 < r2 ) {
            4
        } else {
            0
        }
    }

    /**
     * Modifies a `Table` to include only the subset of cells defined by the specified range.
     *
     * @param table The input `Table` to be modified.
     * @param context A `RangeContext` specifying the start and stop cell references.
     * @return A new `Table` object containing only the cells within the range.
     * @throws RuntimeException if the range is invalid or incorrectly formatted.
     */
    def modify(table: Table, context: RangeContext): Table = {
        val res = new Table() // Create a new empty table to store the result
        var newRows = table.getRows // Initialize with all rows
        var newColumns = table.getColumns // Initialize with all columns
        val formatRegex = "^[A-Z][0-9]+$".r // Regex to validate cell references
        val start = context.start // Start cell reference
        val stop = context.stop // Stop cell reference

        // Validate the format of the start and stop references
        if (formatRegex.matches(start) && formatRegex.matches(stop)) {
            // Parse the row and column indices from the start and stop references
            val FirstColNumber = columnToIndex(start.takeWhile(_.isLetter))
            val FirstRowNumber = start.dropWhile(_.isLetter).toInt - 1

            val SecondColNumber = columnToIndex(stop.takeWhile(_.isLetter))
            val SecondRowNumber = stop.dropWhile(_.isLetter).toInt - 1

            // Check for invalid row numbers
            if (FirstRowNumber < 0 || SecondRowNumber < 0) {
                throw new RuntimeException(s"The row number starts from 1 for both $start and $stop")
            }

            // Compare the start and stop cells to determine the range validity
            val result = compareCells(FirstRowNumber, FirstColNumber, SecondRowNumber, SecondColNumber)
            result match {
                case 1 => // Valid range: adjust rows and columns to include only the specified range
                    newRows = newRows.filter(elem => elem >= FirstRowNumber && elem <= SecondRowNumber)
                    newColumns = newColumns.filter(elem => elem >= FirstColNumber && elem <= SecondColNumber)

                case 2 => // Valid range: stop cell is greater than start cell
                    newRows = newRows.filter(elem => elem >= SecondRowNumber && elem <= FirstRowNumber)
                    newColumns = newColumns.filter(elem => elem >= SecondColNumber && elem <= FirstColNumber)

                case 3 => // Valid range: stop cell is greater than start cell
                    newRows = newRows.filter(elem => elem >= SecondRowNumber && elem <= FirstRowNumber)
                    newColumns = newColumns.filter(elem => elem >= FirstColNumber && elem <= SecondColNumber)

                case 4 => // Valid range: stop cell is greater than start cell
                    newRows = newRows.filter(elem => elem >= FirstRowNumber && elem <= SecondRowNumber)
                    newColumns = newColumns.filter(elem => elem >= SecondColNumber && elem <= FirstColNumber)

                case 0 => // Start and stop cells are the same
                    newColumns = List(FirstColNumber)
                    newRows = List(FirstRowNumber)
            }
        } else {
            // Invalid input format for range references
            throw new RuntimeException("Invalid input format for Range. Please provide inputs in the format 'A1', 'B2', etc.")
        }

        // Set the data, rows, and columns for the result table
        res.setData(table.getData)
        res.setRows(newRows)
        res.setColumns(newColumns)
        res // Return the modified table
    }
}

