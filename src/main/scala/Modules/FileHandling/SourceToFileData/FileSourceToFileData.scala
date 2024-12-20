package Modules.FileHandling.SourceToFileData

import java.io.{FileNotFoundException, IOException}
import java.nio.file.{Files, Paths}
import scala.io.Source
import Models.FileData

/**
 * A class that implements the `SourceToFileData` trait to handle file-based sources.
 *
 * This class provides functionality to check if a given source string is a valid file path
 * and to collect the content of the file into a `FileData` object.
 */
class FileSourceToFileData extends SourceToFileData {
    /**
     * Checks if the given source string represents an existing file path.
     *
     * @param source A string representing the file path.
     * @return `true` if the file exists, `false` otherwise.
     */
    def isSourceType(source: String): Boolean = {
        val filePathRegex = """^[a-zA-Z0-9/]+(\.[a-zA-Z0-9]+)?$""".r
        try {
            // Check if the file exists at the given path
            if (!filePathRegex.matches(source)) {return false}
            Files.exists(Paths.get(source))
        } catch {
            // Return false if an exception occurs (e.g., invalid path)
            case _: Exception => false
        }
    }
    /**
     * Reads the content of the file specified by the source string and returns it as a `FileData` object.
     *
     * @param source A string representing the file path.
     * @return A `FileData` object containing the content of the file.
     * @throws FileNotFoundException if the file does not exist.
     * @throws IOException if an I/O error occurs while reading the file.
     * @throws Exception for any other unexpected errors.
     */
    def collectSource(source: String): FileData = {
        try {
            // Open the file and read its content
            val fileSource = Source.fromFile(source)
            val data = fileSource.mkString.trim
                .replaceAll("\r\n", "\n") // Normalize Windows newlines to Unix-style

            fileSource.close() // Ensure the file is properly closed
            FileData(data) // Return the file content wrapped in a `FileData` object
        } catch {
            // Handle specific exceptions and provide informative error messages
            case e: FileNotFoundException =>
                println(s"File not found: $source")
                throw new FileNotFoundException(s"File '$source' does not exist.")
            case e: IOException =>
                println(s"An I/O error occurred while trying to read the file: $source")
                throw new IOException(s"An error occurred while accessing file '$source'.")
            case e: Exception =>
                println(s"An unexpected error occurred: ${e.getMessage}")
                throw e
        }
    }
}
