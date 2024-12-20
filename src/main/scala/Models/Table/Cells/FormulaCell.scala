package Models.Table.Cells

/**
 * A case class representing a formula cell in a table.
 * This class extends the `Cell` abstract class, specifically for cells that contain formulas.
 * The content of the cell is a string representing the formula, and the cell type is defined as `"Formula"`.
 *
 * @param cellContent A string representing the formula contained in the cell.
 */
case class FormulaCell(override val cellContent: String)
    extends Cell[String](
        cellContent,          // Pass the formula content to the base `Cell` class.
        typeOfCell = "Formula" // Specify the type of cell as "Formula".
    )
