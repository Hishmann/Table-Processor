package Modules.TableFunctions.TablePrinter.TablePrinterText

import Models.Table.Table
import Modules.TableFunctions.ReferencingIndices

/**
 * A class that implements `TablePrinterText` to print a `Table` as Markdown-style text.
 *
 * This class provides functionality to render a `Table` object into a Markdown-compatible table format.
 * It can include optional headers and ensures proper alignment of rows and columns.
 *
 * @param header A boolean indicating whether to include column headers in the Markdown output (default: `false`).
 */
class TablePrinterMDText(val header: Boolean = false) extends TablePrinterText {

    /**
     * Converts a `Table` into a Markdown-style table representation.
     *
     * @param table The `Table` object to be converted into a Markdown table format.
     * @return A string representation of the table in Markdown format.
     */
    override def print(table: Table): String = {
        val data = table.getData        // Retrieve the table data
        val rows = table.getRows        // Retrieve row indices
        val columns = table.getColumns        // Retrieve Column indices
        val columnsHeader = columns.map(i => ReferencingIndices.indexToColumn(i)) // Map column indices to column headers

        val columnToLetterMap: Map[String, Int] = columnsHeader.zip(columns).toMap

        // Determine the maximum width of each column, including row headers
        val rowHeaderWidth = rows.map(_.toString.length).max
        val columnWidths = columns.map { col =>
            // Get the maximum width of all cell contents in this column
            val cellContentWidths = rows.map(row => data(row)(col).cellContent.toString.length)
            // Include the column header width for comparison
            val columnHeaderWidth = ReferencingIndices.indexToColumn(col).length
            // Return the maximum of cell content widths and the column header width
            (cellContentWidths :+ columnHeaderWidth).max
        }

        var headerContent = "" // Initialize the header content
        var separator = ""     // Initialize the Markdown separator row
        var rowContent: List[String] = List() // Initialize the rows as a list of strings

        var i = -1
        if (header) {
            // Build the header row with column names and proper alignment
            headerContent = "| " + " ".padTo(rowHeaderWidth, ' ') + " | " +
                columnsHeader.zip(columnWidths).map {
                    case (col, width) => col.reverse.padTo(width, ' ').reverse // Align column headers
                }.mkString(" | ") + " |" + "\n"

            // Build the Markdown separator row with dashes
            separator = "|-" + "-" * rowHeaderWidth + "-|-" +
                columnWidths.map(width => "-" * width).mkString("-|-") + "-|" + "\n"

            // Build each row with aligned values and row numbers
            for (row <- rows) {
                rowContent = rowContent ++ List(
                    "| " + row.toString.padTo(rowHeaderWidth, ' ') + " | " +
                        columns.map { colIndex =>
                            i += 1
                            data(row)(colIndex).cellContent.toString.reverse.padTo(columnWidths(i), ' ').reverse
                        }.mkString(" | ") + " |"
                )
                i = -1
            }
        } else {
            // No headers: create a blank header and separator
            headerContent = "| " +
                columns.zip(columnWidths).map {
                    case (_, width) => " " * width
                }.mkString(" | ") + " |" + "\n"

            // Create a separator row with dashes
            separator = "|-" + columnWidths.map(width => "-" * width).mkString("-|-") + "-|" + "\n"

            // Build each row with aligned values but no row numbers
            for (row <- rows) {
                rowContent = rowContent ++ List(
                    "| " +
                        columns.map { colIndex =>
                            i += 1
                            data(row)(colIndex).cellContent.toString.reverse.padTo(columnWidths(i), ' ').reverse
                        }.mkString(" | ") + " |"
                )
                i = -1
            }
        }

        // Combine the header, separator, and row content into the final Markdown table
        var resultStr = headerContent + separator
        rowContent.foreach(i => resultStr += i + "\n") // Append each row to the result
        resultStr
    }
}

