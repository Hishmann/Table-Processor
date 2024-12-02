package LoadData
import Models.Parameters.LoadDataTableParameters
import Models.Table.Table
import TableCreator.TableCreatorFromListStrings
import LoadData.SourceLoader.{SourceLoader, FileSourceLoader, WebSourceLoader}
import LoadData.ParseMethod.TableLoader.{CSVTableLoader, XMLTableLoader}

class LoadDataTable(val LDTParam: LoadDataTableParameters) {
    private val allSourceMethods: List[SourceLoader] = List(
        new FileSourceLoader(LDTParam.inputFile),
        new WebSourceLoader(LDTParam.inputFile)
    )

    def load: Table = {
        var result: Table = Table()
        val sourceLoaderPossible = allSourceMethods.filter(x => x.isSourceType)
        if (sourceLoaderPossible.isEmpty) {
            throw new RuntimeException("Issues with input source instruction.")
        }
        val sourceLoader = sourceLoaderPossible.head
        val tableCreator = new TableCreatorFromListStrings

        if (LDTParam.format == "csv") {
            result = new CSVTableLoader(sourceLoader, tableCreator, LDTParam.separator).parse
        } else if (LDTParam.format == "xml") {
            result = new XMLTableLoader(sourceLoader, tableCreator).parse
        } else {
            throw new RuntimeException("Unsupported file type requested.")
        }
        result
    }
}


