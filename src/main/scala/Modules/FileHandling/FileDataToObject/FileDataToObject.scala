package Modules.FileHandling.FileDataToObject

import Models.FileData

/**
 * A trait for converting file data into an object of type `T`.
 *
 * This trait defines a contract for transforming the contents of a `FileData` object
 * into a result of type `T`. It can be implemented to parse or process file data into
 * structured objects, domain-specific data types, or other representations.
 *
 * @tparam T The type of the result produced by the transformation.
 */
trait FileDataToObject[T] {

    /**
     * Processes the source file data and converts it into an object of type `T`.
     *
     * @param sourceData A `FileData` object containing the content to be processed.
     * @return An object of type `T` resulting from the transformation of the file data.
     */
    def getResult(sourceData: FileData): T
}
