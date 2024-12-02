package LoadData.SourceLoader

import LoadData.SourceLoader.SourceLoader

class WebSourceLoader(source: String) extends SourceLoader(source) {
    def isSourceType: Boolean = {
        source.startsWith("http://") || source.startsWith("https://")
    }
    def collectSource: String = ""
}
