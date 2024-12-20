package TableProcessor.CommandUtils

// Import necessary modules and command variants for defining command-line options
import Modules.Commands.Variants.{defaultCommand, repeatCommand, toggleCommand}
import Modules.Commands.Command

/**
 * CommandUtils provides command definitions and utilities for the table processor application.
 * It defines all the command-line options supported by the program.
 */
object CommandUtils {

    // Command names for various command-line options
    val inputFileCommandName = "input-file"               // Command to specify the input file
    val inputSeparatorCommandName = "input-separator"    // Command for specifying input file separator
    val separatorCommandName = "input-separator"         // Alternative command for input separator
    val outputFileCommandName = "output-file"            // Command to specify the output file
    val outputSeparatorCommandName = "output-separator"  // Command for output file separator
    val outputformatCommandName = "output-format"        // Command for output file format
    val stdOutputCommandName = "stdout"                  // Command to enable standard output
    val rangeCommandName = "range"                       // Command for specifying a range of rows
    val helpCommandName = "help"                         // Command to display help information
    val filterCommandName = "filter"                     // Command to filter rows based on conditions
    val filterEmptyCommandName = "filter-is-empty"       // Command to filter rows with empty cells
    val filterNotEmptyCommandName = "filter-is-not-empty" // Command to filter rows with non-empty cells
    val headerCommandName = "header"                     // Command to toggle headers in output

    /**
     * List of available commands with their configurations.
     * Each command is instantiated as a specific type (default, toggle, or repeat).
     */
    val commandList: List[Command] = List(

        // Command for specifying the input file location
        new defaultCommand(
            keyStr = inputFileCommandName,
            helpStr = "--input-file [filepath/html/XML]\nCommand line option to mention input file location"
        ),

        // Command for specifying the output file
        new defaultCommand(
            keyStr = outputFileCommandName,
            helpStr = "--output-file [FILE]\nthe file to output the table to (optional)"
        ),

        // Command for specifying the input separator
        new defaultCommand(
            keyStr = inputSeparatorCommandName,
            helpStr = "--input-separator [STRING]\nthe separator for input (optional, defaults to ',')"
        ),

        // Command for specifying the output separator for CSV
        new defaultCommand(
            keyStr = outputSeparatorCommandName,
            helpStr = "--output-separator [STRING]\nfor CSV output: the separator in the output file (optional, defaults to \",\")"
        ),

        // Command for specifying the general input file separator
        new defaultCommand(
            keyStr = separatorCommandName,
            helpStr = "--separator [SEPARATOR]\nCommand line option to mention input file separator to read along"
        ),

        // Command for specifying the output format
        new defaultCommand(
            keyStr = outputformatCommandName,
            helpStr = "--output-format (csv|md)\nCommand line option to mention output file format where TYPE = csv or xml"
        ),

        // Command for specifying a range of rows to include in the output
        new defaultCommand(
            keyStr = rangeCommandName,
            inputCount = 2, // Requires two arguments: FROM and TO
            helpStr = "--range [FROM] [TO]\nCommand line option to mention the range for printing the output table"
        ),

        // Command to display help information for all commands
        new toggleCommand(
            keyStr = helpCommandName,
            helpStr = "--help\nCommand line option to show and explain all the options"
        ),

        // Command to toggle headers in the output
        new toggleCommand(
            keyStr = headerCommandName,
            helpStr = "--headers\nturns on printing of headers (optional, by default, they are not printed)"
        ),

        // Command to enable printing the table to standard output
        new toggleCommand(
            keyStr = stdOutputCommandName,
            helpStr = "--stdout\nprint the table to the standard output (optional, by default true)"
        ),

        // Command to filter rows based on a condition in a specific column
        new repeatCommand(
            keyStr = filterCommandName,
            inputCount = 3, // Requires COLUMN, OPERATOR, and VALUE as arguments
            helpStr = "--filter [COLUMN] [OPERATOR] [VALUE]\n" +
                "filter out lines that fail inequality checks on cells in columns (optional, can be repeated)"
        ),

        // Command to filter rows with empty cells in the specified column
        new repeatCommand(
            keyStr = filterEmptyCommandName,
            helpStr = "--filter-is-empty [COLUMN]\nfilter out lines with non-empty cells on column (optional, can be repeated)"
        ),

        // Command to filter rows with non-empty cells in the specified column
        new repeatCommand(
            keyStr = filterNotEmptyCommandName,
            helpStr = "--filter-is-not-empty [COLUMN]\nfilter out lines with empty cells on column (optional, can be repeated)"
        )
    )

}
