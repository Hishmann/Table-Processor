package Modules.FileHandling.FileDataToObject.FileDataToTextTable

import Models.Table.TextTable
import Models.FileData

/**
 * A class for converting CSV file data into a `TextTable`.
 *
 * This class implements the `FileDataToTextTable` trait, providing functionality
 * to parse CSV file content into a table structure represented by the `TextTable` class.
 *
 * @param separator The character used to separate columns in the CSV file.
 */
class CSVFileDataToTextTable(val separator: String) extends FileDataToTextTable {
    /**
     * Processes the CSV file data and converts it into a `TextTable`.
     *
     * @param sourceData A `FileData` object containing the content of the CSV file as a string.
     * @return A `TextTable` representing the rows and columns of the CSV file.
     */
    def getResult(sourceData: FileData): TextTable = {
        // Extract the raw data string from the `FileData` object
        val data: String = sourceData.data
        // Split the input data into rows, removing any leading or trailing whitespace from each row
        val rows = data.split("\n").map(_.trim).toList
        // Split each row into columns using the specified separator, preserving empty fields
        val table = rows.map { line =>
            line.split(separator, -1).map(_.trim).toList
        }
        // Construct and return a new `TextTable` object using the parsed data
        new TextTable(table)
    }
}
