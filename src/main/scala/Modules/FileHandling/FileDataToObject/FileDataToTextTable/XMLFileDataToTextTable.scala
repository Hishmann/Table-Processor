package Modules.FileHandling.FileDataToObject.FileDataToTextTable

import Models.Table.TextTable
import Models.FileData

class XMLFileDataToTextTable extends FileDataToTextTable {
    def getResult(sourceData: FileData): TextTable = {
        new TextTable(List())
    }
}
