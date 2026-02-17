package TableProcessor.TableStringToOutput

// Import necessary modules for handling command arguments and writing to files
import Models.CommandModels.CommandArgumentsCollection
import TableProcessor.CommandUtils.CommandUtils
import Modules.FileHandling.StringToSource.StringToFile.StringToFile

/**
 * TableStringToOutputFile handles writing table content to an output file and/or printing it to standard output.
 * @param args CommandArgumentsCollection containing parsed command-line arguments.
 */
class TableStringToOutputFile(args: CommandArgumentsCollection) {

    /**
     * Determines the output file name from the command-line arguments.
     * Defaults to an empty string if no output file is specified.
     */
    val outputFile: String = args.argFromCommOrEmpty(CommandUtils.outputFileCommandName) match {
        case Some(value) => value.arguments.head // Use the specified output file
        case None => "" // Default to no output file
    }

    /**
     * Determines whether standard output should be used.
     * Defaults to `false` if the stdout option is not specified.
     */
    val stdout: Boolean = args.argFromCommOrEmpty(CommandUtils.stdOutputCommandName) match {
        case Some(_) => true // Enable standard output if the option is specified
        case None => false // Default to not using standard output
    }

    /**
     * Determines the output format from the command-line arguments.
     * Defaults to the file extension of the output file if no format is specified.
     */
    val format: String = args.argFromCommOrEmpty(CommandUtils.outputformatCommandName) match {
        case Some(value) => value.arguments.head // Use the specified format
        case None => outputFile.takeRight(3) // Default to file extension
    }

    /**
     * Outputs the table content to a file and/or prints it to standard output based on configuration.
     * @param toFileContent The content to write to the output file.
     * @param toStdOutContent The content to print to standard output.
     */
    def outputIt(toFileContent: String, toStdOutContent: String): Unit = {
        if (outputFile.nonEmpty) {
            // Write content to the specified output file
            val stringToFile: StringToFile = new StringToFile
            stringToFile.toSource(content = toFileContent, source = outputFile)

            // Optionally print to standard output
            if (stdout) {
                println(toStdOutContent)
            }
        } else {
            // Print to standard output if no output file is specified
            println(toStdOutContent)
        }
    }
}
