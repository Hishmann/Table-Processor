package Models.Table.Cells

/**
 * A case class representing an empty cell in a table.
 *
 * This class extends the `Cell` abstract class, specifically for cells that are considered empty.
 * The content of an empty cell is a single space (`" "`), and its type is defined as `"Empty"`.
 */
case class EmptyCell() extends Cell[String](
    cellContent = " ",      // The content of an empty cell is a single space.
    typeOfCell = "Empty"    // The type of the cell is specified as "Empty".
)
