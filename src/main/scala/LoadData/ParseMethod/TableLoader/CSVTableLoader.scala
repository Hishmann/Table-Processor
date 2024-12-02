package LoadData.ParseMethod.TableLoader

import LoadData.ParseMethod.ParseLoader
import Models.Table.Table
import LoadData.SourceLoader.SourceLoader
import TableCreator.TableCreator

class CSVTableLoader(delegate: SourceLoader, creator: TableCreator[List[List[String]]],
                     val separator: String)
    extends TableLoader[List[List[String]]](delegate, creator) {

    def parse: Table = {
        val data: String = delegate.collectSource
        // Split the input data into rows, filter out any empty rows
        val rows = data.split("\n").map(_.trim).toList
        // Split each row into columns, preserving empty fields
        val table = rows.map { line =>
            line.split(separator, -1).toList
        }
        creator.dataInitializer(table)
    }
}
