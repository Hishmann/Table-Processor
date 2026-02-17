package Modules.FileHandling.SourceToFileData

import Models.FileData

/**
 * A trait for converting a source string into a `FileData` object.
 *
 * This trait defines a contract for identifying and processing different types of sources.
 * It includes methods for determining if a given source string matches a specific type
 * and for collecting the source's content into a `FileData` object.
 */
trait SourceToFileData {
    /**
     * Determines if the given source string matches the expected source type.
     *
     * @param source A string representing the source (e.g., file path, URL).
     * @return `true` if the source matches the expected type, `false` otherwise.
     */
    def isSourceType(source: String): Boolean
    /**
     * Processes the given source string and collects its content into a `FileData` object.
     *
     * @param source A string representing the source (e.g., file path, URL).
     * @return A `FileData` object containing the content of the source.
     */
    def collectSource(source: String): FileData
}
