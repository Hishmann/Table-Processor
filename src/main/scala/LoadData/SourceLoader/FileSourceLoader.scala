package LoadData.SourceLoader

import LoadData.SourceLoader.SourceLoader
import java.io.{FileNotFoundException, IOException}
import java.nio.file.{Files, Paths}
import scala.io.Source

class FileSourceLoader(source: String) extends SourceLoader(source) {
    def isSourceType: Boolean = {
        try {
            Files.exists(Paths.get(source))
        } catch {
            case _: Exception => false
        }
    }
    def collectSource: String = {
        try {
            val fileSource = Source.fromFile(source)
            val data = fileSource.mkString
            fileSource.close()
            data
        } catch {
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
