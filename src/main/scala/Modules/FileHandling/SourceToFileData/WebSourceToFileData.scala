package Modules.FileHandling.SourceToFileData

import Models.FileData

class WebSourceToFileData extends SourceToFileData {
    def isSourceType(source: String): Boolean = {
        source.startsWith("http://") || source.startsWith("https://")
    }
    def collectSource(source: String): FileData = FileData("")
}
