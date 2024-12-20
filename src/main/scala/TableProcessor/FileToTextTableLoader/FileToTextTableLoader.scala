package TableProcessor.FileToTextTableLoader

// Import necessary modules and classes for file handling and command argument processing
import Models.CommandModels.CommandArgumentsCollection
import Modules.FileHandling.FileDataToObject.FileDataToTextTable.{CSVFileDataToTextTable, FileDataToTextTable, XMLFileDataToTextTable}
import TableProcessor.CommandUtils.CommandUtils

/**
 * FileToTextTableLoader determines the appropriate loader for processing input files
 * based on the format and separator specified in the command arguments.
 * @param args CommandArgumentsCollection containing the parsed command-line arguments.
 */
class FileToTextTableLoader(args: CommandArgumentsCollection) {

    // Determine the file format from the input file's extension (default: "csv").
    val format: String = args.argFromCommOrEmpty(CommandUtils.inputFileCommandName) match {
        case Some(value) => value.arguments.head.takeRight(3) // Extract the last three characters (file extension)
        case None => "csv" // Default to "csv" if no input file is specified
    }

    // Determine the input separator for CSV files (default: ",").
    val inputSeparator: String = args.argFromCommOrEmpty(CommandUtils.inputSeparatorCommandName) match {
        case Some(value) => value.arguments.head // Use the provided separator
        case None => "," // Default to a comma if no separator is specified
    }

    /**
     * Returns the appropriate FileDataToTextTable instance based on the file format.
     * @return FileDataToTextTable instance for handling the specified format.
     * @throws IllegalArgumentException if the file format is unsupported.
     */
    def getTextTableLoader: FileDataToTextTable = {
        format.toLowerCase match {
            case "csv" => new CSVFileDataToTextTable(inputSeparator) // Create loader for CSV files
            case "xml" => new XMLFileDataToTextTable()              // Create loader for XML files
            case _ => throw new IllegalArgumentException("Unsupported format!") // Error for unsupported formats
        }
    }
}
