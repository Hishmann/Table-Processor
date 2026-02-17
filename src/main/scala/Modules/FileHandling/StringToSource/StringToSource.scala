package Modules.FileHandling.StringToSource

/**
 * A trait for converting a string into a specified source type.
 *
 * This trait defines a contract for transforming a string into a source of type `T`.
 * It includes a method for implementing the conversion or writing logic based on the provided string content.
 *
 * @tparam T The type of the target source to which the string will be converted or written.
 */
trait StringToSource[T] {
    /**
     * Converts or writes the given string content to the specified source.
     *
     * @param content A string containing the data to be transformed or written.
     * @param source An instance of the target source of type `T`.
     */
    def toSource(content: String, source: T): Unit
}
