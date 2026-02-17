package TableProcessor.TablePrintLoader

// Import necessary modules and utilities for command argument handling and table printing
import Models.CommandModels.CommandArgumentsCollection
import TableProcessor.CommandUtils.CommandUtils
import Modules.TableFunctions.TablePrinter.TablePrinterText.TablePrinterText
import Modules.TableFunctions.TablePrinter.TablePrinterText.{TablePrinterMDText, TablePrinterCSVText}

/**
 * TablePrintStdOutLoader determines the appropriate table printer for printing
 * tables to standard output, based on the specified format and settings.
 * @param args CommandArgumentsCollection containing parsed command-line arguments.
 */
class TablePrintStdOutLoader(args: CommandArgumentsCollection) {

    /**
     * Extracts the output format for standard output from the command-line arguments.
     * Defaults to "csv" if no format is specified.
     */
    val format: String = args.argFromCommOrEmpty(CommandUtils.outputformatCommandName) match {
        case Some(value) => value.arguments.head // Use the specified format
        case None => "csv" // Default format
    }

    /**
     * Determines if headers should be included in the standard output.
     * Defaults to `false` if the header option is not specified.
     */
    val header: Boolean = args.argFromCommOrEmpty(CommandUtils.headerCommandName) match {
        case Some(_) => true // Headers are included if the option is specified
        case None => false // Default to no headers
    }

    /**
     * Returns the appropriate TablePrinterText instance for printing to standard output.
     * @return A TablePrinterText instance configured for the specified format and header settings.
     * @throws IllegalArgumentException if the specified format is unsupported.
     */
    def getLoader: TablePrinterText = {
        format.toLowerCase match {
            case "csv" => new TablePrinterCSVText(separator = ",", header = header) // CSV printer with header option
            case "md" => new TablePrinterMDText(header = header)                   // Markdown printer with header option
            case _ => throw new IllegalArgumentException("Unsupported format!")   // Error for unsupported formats
        }
    }
}
