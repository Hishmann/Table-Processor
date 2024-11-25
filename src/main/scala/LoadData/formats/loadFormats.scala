package LoadData.formats
import java.io.{FileNotFoundException, IOException}
import java.nio.file.{Files, Paths}
import scala.io.Source

class loadFormatParameters(val inputFileDest: String = "") {}

trait loadFormats {
    def isValidLoadType: Boolean
    def load: String
    def updateParams(p: loadFormatParameters): Unit = {}
}

class file(private var dir: String = "") extends loadFormats {
    override def updateParams(p: loadFormatParameters): Unit = {
        dir = p.inputFileDest
    }
    override def isValidLoadType: Boolean  = {
        try {
            Files.exists(Paths.get(dir))
        } catch {
            case _: Exception => false
        }
    }
    override def load: String = {
        try {
            val source = Source.fromFile(dir)
            val data = source.mkString
            source.close()
            data
        } catch {
            case e: FileNotFoundException =>
                println(s"File not found: $dir")
                throw new FileNotFoundException(s"File '$dir' does not exist.")
            case e: IOException =>
                println(s"An I/O error occurred while trying to read the file: $dir")
                throw new IOException(s"An error occurred while accessing file '$dir'.")
            case e: Exception =>
                println(s"An unexpected error occurred: ${e.getMessage}")
                throw e
        }
    }
}

class weblink(private var link: String = "") extends loadFormats {
    override def isValidLoadType: Boolean = {
        link.startsWith("http://") || link.startsWith("https://")
    }
    override def load: String = { "" }
    override def updateParams(p: loadFormatParameters): Unit = {}
}