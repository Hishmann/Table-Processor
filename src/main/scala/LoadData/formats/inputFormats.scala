package LoadData.formats

class inputFormatParameters(val CsvSeparator: String = "", val inputFormat: String = "csv") {}

trait inputFormats {
    val formatName: String = ""
    def parse(data: String): List[List[String]]
    def updateParams(p: inputFormatParameters): Unit
}

class csv(private var separator: String = ",") extends inputFormats {
    override val formatName = "csv"
    override def parse(data: String): List[List[String]] = {
        // Split the input data into rows, filter out any empty rows
        val rows = data.split("\n").map(_.trim).toList
        // Split each row into columns, preserving empty fields
        rows.map { line =>
            line.split(separator, -1).toList
        }
    }

    override def updateParams(p: inputFormatParameters): Unit = {
        separator = if (p.CsvSeparator.isEmpty) "," else p.CsvSeparator
    }
}

class xml() extends inputFormats {
    override val formatName = "xml"
    override def parse(data: String): List[List[String]] = {
        List()
    }
    override def updateParams(p: inputFormatParameters): Unit = {}
}

