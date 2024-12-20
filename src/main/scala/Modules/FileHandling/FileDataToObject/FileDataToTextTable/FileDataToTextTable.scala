package Modules.FileHandling.FileDataToObject.FileDataToTextTable

import Models.FileData
import Models.Table.TextTable
import Modules.FileHandling.FileDataToObject.FileDataToObject

/**
 * A trait for converting file data into a `TextTable`.
 * This trait extends `FileDataToObject` with a specific implementation for transforming
 * a `FileData` object into a `TextTable`. It defines the `getResult` method to process
 * the file content and produce a table structure represented by the `TextTable` class.
 */
trait FileDataToTextTable extends FileDataToObject[TextTable] {
    /**
     * Processes the source file data and converts it into a `TextTable`.
     *
     * @param sourceData A `FileData` object containing the content to be processed.
     * @return A `TextTable` representation of the file data.
     */
    override def getResult(sourceData: FileData): TextTable
}
