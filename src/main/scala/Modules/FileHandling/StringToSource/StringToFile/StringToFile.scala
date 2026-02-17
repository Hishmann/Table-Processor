package Modules.FileHandling.StringToSource.StringToFile

import java.io._

import Modules.FileHandling.StringToSource.StringToSource

/**
 * A class that implements the `StringToSource` trait to write string content to a file.
 *
 * This class provides functionality to write a string to a file specified by a file path.
 */
class StringToFile extends StringToSource[String] {

    /**
     * Writes the given string content to the specified file.
     *
     * @param content A string containing the data to be written.
     * @param source A string representing the file path where the content will be written.
     * @throws IOException if an error occurs while writing to the file.
     */
    def toSource(content: String, source: String): Unit = {
        // Create a File object for the specified file path
        val file = new File(source)

        try {
            // Create parent directories if they do not exist
            val parentDir = file.getParentFile
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs()
            }

            // Create the file if it does not exist
            if (!file.exists()) {
                file.createNewFile()
            }

            // Create a BufferedWriter to write content to the file
            val writer = new BufferedWriter(new FileWriter(file))
            try {
                // Write the content to the file
                writer.write(content)
                println(s"Content successfully written to $source")
            } finally {
                // Ensure the writer is closed to release resources
                writer.close()
            }
        } catch {
            // Handle any IOException that occurs during the file writing process
            case e: IOException =>
                println(s"An error occurred: ${e.getMessage}")
        }
    }
}
