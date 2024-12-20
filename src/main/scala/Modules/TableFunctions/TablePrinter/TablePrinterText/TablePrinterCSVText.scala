package Modules.TableFunctions.TablePrinter.TablePrinterText
import Models.Table.Table
import Modules.TableFunctions.ReferencingIndices

/**
 * A class that implements `TablePrinterText` to print a `Table` as a CSV-style text representation.
 *
 * This class provides functionality to render a `Table` object into a CSV format string.
 * The output can include optional headers and uses a configurable separator for columns.
 *
 * @param separator The character used to separate columns in the CSV output.
 * @param header A boolean indicating whether to include column headers in the output (default: `false`).
 */
class TablePrinterCSVText(val separator: String, val header: Boolean = false) extends TablePrinterText {

    /**
     * Converts a `Table` into a CSV-style text representation.
     *
     * @param table The `Table` object to be converted into a CSV format string.
     * @return A string representation of the table in CSV format.
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

        var headerContent = ""         // Initialize the header content
        var rowContent: List[String] = List() // Initialize the rows as a list of strings

        var i = -1
        if (header) {
            // Build the header row with spaces for alignment
            headerContent = " " * (rowHeaderWidth) + separator + " " * (rowHeaderWidth) +
                columnsHeader.zip(columnWidths).map {
                    case (col, width) => col.reverse.padTo(width, ' ').reverse // Align column headers
                }.mkString(separator + " ") + "\n"

            for (row <- rows) {
                rowContent = rowContent ++ List(
                    (row + 1).toString.padTo(rowHeaderWidth, ' ') + separator + " " +
                    columns.map { colIndex =>
                        i += 1
                        data(row)(colIndex).cellContent.toString.reverse.padTo(columnWidths(i), ' ').reverse
                    }.mkString(separator + " ")
                )
                i = -1
            }

        } else {

            for (row <- rows) {
                rowContent = rowContent ++ List(
                        columns.map { colIndex =>
                            i += 1
                            data(row)(colIndex).cellContent.toString.reverse.padTo(columnWidths(i), ' ').reverse
                        }.mkString(separator + " ")
                )
                i = -1
            }
        }

        // Combine the header and rows into the final CSV output
        var result = headerContent
        rowContent.foreach(i => {
            result += i + "\n"
        })
        result
    }
}
