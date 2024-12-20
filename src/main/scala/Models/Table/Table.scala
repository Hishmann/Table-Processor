package Models.Table

import Models.Table.Cells.Cell

/**
 * A case class representing a table structure with rows, columns, and data.
 *
 * This class encapsulates a two-dimensional collection of `Cell` objects,
 * along with metadata about rows and columns. It provides methods for accessing
 * and updating the table's data, rows, and columns.
 */
case class Table() {
    // A private list representing the data in the table as a two-dimensional collection of cells.
    private var data: List[List[Cell[_]]] = List()
    // A private list representing the indices of the rows in the table.
    private var rows: List[Int] = List()
    // A private list representing the indices of the columns in the table.
    private var columns: List[Int] = List()
    /**
     * Gets the list of row indices.
     * @return A list of integers representing the indices of the rows.
     */
    def getRows: List[Int] = rows
    /**
     * Sets the list of row indices.
     * @param n A list of integers to set as the row indices.
     */
    def setRows(n: List[Int]): Unit = { rows = n }
    /**
     * Gets the list of column indices.
     * @return A list of integers representing the indices of the columns.
     */
    def getColumns: List[Int] = columns
    /**
     * Sets the list of column indices.
     * @param n A list of integers to set as the column indices.
     */
    def setColumns(n: List[Int]): Unit = { columns = n }
    /**
     * Gets the table data.
     * @return A two-dimensional list of `Cell` objects representing the table's data.
     */
    def getData: List[List[Cell[_]]] = data
    /**
     * Sets the table data and updates the row and column indices accordingly.
     * @param newData A two-dimensional list of `Cell` objects to set as the table's data.
     */
    def setData(newData: List[List[Cell[_]]]): Unit = {
        data = newData
        // Update the row indices to match the new number of rows.
        rows = (0 to data.length - 1).toList
        // Update the column indices to match the new number of columns in the first row.
        columns = (0 to data.head.length - 1).toList
    }
}
