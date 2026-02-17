package Modules.TableFunctions.TablePrinter.TablePrinterText

import Modules.TableFunctions.TablePrinter.TablePrinter
import Models.Table.Table

/**
 * A trait for printing a `Table` as a text-based representation.
 *
 * This trait extends the `TablePrinter` trait with the specific type parameter `String`,
 * providing a contract for transforming a `Table` into a string-based textual format.
 * It can be implemented to render tables as plain text, CSV, or other text formats.
 */
trait TablePrinterText extends TablePrinter[String] {

    /**
     * Converts the given `Table` into a textual representation.
     *
     * @param table The `Table` object to be converted into a string.
     * @return A `String` representation of the table.
     */
    def print(table: Table): String
}
