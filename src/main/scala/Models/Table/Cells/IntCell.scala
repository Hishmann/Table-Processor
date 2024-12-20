package Models.Table.Cells

/**
 * A case class representing a cell in a table that holds integer data.
 * This class extends the `Cell` abstract class, specifically for cells that store integer values.
 * The content of the cell is an integer, and the cell type is defined as `"Int"`.
 *
 * @param cellContent An integer value representing the content of the cell.
 */
case class IntCell(override val cellContent: Int)
    extends Cell[Int](
        cellContent,          // Pass the integer content to the base `Cell` class.
        typeOfCell = "Int"    // Specify the type of cell as "Int".
    )
