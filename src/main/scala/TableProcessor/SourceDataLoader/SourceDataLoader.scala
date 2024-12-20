package TableProcessor.SourceDataLoader

// Import necessary modules for handling different types of sources
import Modules.FileHandling.SourceToFileData.{FileSourceToFileData, SourceToFileData, WebSourceToFileData}

/**
 * SourceDataLoader determines the appropriate loader for handling input sources (file or web).
 * @param inputSource The input source as a string (e.g., file path or URL).
 */
class SourceDataLoader(val inputSource: String) {

    // List of available source loaders, supporting file-based and web-based sources.
    private val LoaderList: List[SourceToFileData] = List(
        new FileSourceToFileData, // Loader for file sources
        new WebSourceToFileData  // Loader for web sources
    )

    /**
     * Returns the appropriate SourceToFileData instance based on the input source type.
     * @return SourceToFileData instance capable of handling the specified input source.
     * @throws IllegalArgumentException if the input source type is not recognized.
     */
    def getSourceLoader: SourceToFileData = {
        val loader = LoaderList.find(_.isSourceType(inputSource)).getOrElse(
            throw new IllegalArgumentException("Unknown source type!") // Error if no matching loader is found
        )
        loader // Return the matched loader
    }
}
