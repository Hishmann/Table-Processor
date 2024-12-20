package Models.Table

/**
 * A class representing a table structure with string data.
 *
 * This class encapsulates a two-dimensional list of strings (`data`) that represents
 * the contents of the table. Each inner list represents a row in the table.
 *
 * @param data A two-dimensional list of strings representing the rows and columns of the table.
 */
case class TextTable(val data: List[List[String]]) {
}
