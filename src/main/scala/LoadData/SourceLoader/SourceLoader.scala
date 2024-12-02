package LoadData.SourceLoader
 
abstract class SourceLoader(val source: String = "") {
    def isSourceType: Boolean
    def collectSource: String
}
