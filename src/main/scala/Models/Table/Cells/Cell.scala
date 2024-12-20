package Models.Table.Cells

/**
 * An abstract class representing a generic cell in a table.
 * This class serves as a base for defining cells that hold data of a specific type.
 * Each cell contains its content and a description of the type of cell.
 * @tparam T The type of data stored in the cell.
 * @param cellContent The content of the cell, of type `T`.
 * @param typeOfCell A string describing the type of the cell (e.g., "IntCell", "StringCell").
 */
abstract class Cell[T](val cellContent: T, val typeOfCell: String) {
}
