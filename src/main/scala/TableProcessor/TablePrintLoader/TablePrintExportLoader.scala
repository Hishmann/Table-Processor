package TableProcessor.TablePrintLoader

// Import necessary modules and utilities for handling command arguments and table printing
import Models.CommandModels.CommandArgumentsCollection
import Modules.TableFunctions.TablePrinter.TablePrinterText.{TablePrinterCSVText, TablePrinterMDText, TablePrinterText}
import TableProcessor.CommandUtils.CommandUtils

/**
 * TablePrintExportLoader is responsible for determining the appropriate table printer
 * based on the export format specified in the command-line arguments.
 * @param args CommandArgumentsCollection containing parsed command-line arguments.
 */
class TablePrintExportLoader(args: CommandArgumentsCollection) {

    /**
     * Extracts the output file format from the command-line arguments.
     * Defaults to "csv" if no output file format is specified.
     */
    val format: String = args.argFromCommOrEmpty(CommandUtils.outputFileCommandName) match {
        case Some(value) =>
            value.arguments.head.substring(value.arguments.head.lastIndexOf('.') + 1) // Extract file extension
        case None => "csv" // Default format
    }

    /**
     * Extracts the separator for CSV output from the command-line arguments.
     * Defaults to a comma (",") if no separator is specified.
     */
    val csvSep: String = args.argFromCommOrEmpty(CommandUtils.outputSeparatorCommandName) match {
        case Some(value) => value.arguments.head // Use the provided separator
        case None => "," // Default separator
    }

    /**
     * Returns the appropriate TablePrinterText instance based on the specified format.
     * @return A TablePrinterText instance for printing tables in the desired format.
     * @throws IllegalArgumentException if the specified format is unsupported.
     */
    def getLoader: TablePrinterText = {
        format.toLowerCase match {
            case "csv" => new TablePrinterCSVText(separator = csvSep) // CSV printer with the specified separator
            case "md" => new TablePrinterMDText()                   // Markdown printer
            case _ => throw new IllegalArgumentException("Unsupported format!") // Error for unsupported formats
        }
    }
}
