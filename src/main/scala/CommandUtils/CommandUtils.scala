package CommandUtils

import Commands.Variants.{defaultcommand, repeatcommand, togglecommand}
import Commands.command

object CommandUtils {

    val inputFileCommandName = "input-file"
    val separatorCommandName= "input-separator"
    val outputSeparatorCommandName = "output-separator"
    val formatCommandName = "format"
    val rangeCommandName = "range"
    val helpCommandName = "help"
    val filterCommandName = "filter"
    val filterEmptyCommandName = "filter-is-empty"

    val commandList: List[command] = List(
        new defaultcommand(keyStr = inputFileCommandName,
            helpStr = "--input-file [filepath/html/XML]\nCommand line option to mention input file location"),

        new defaultcommand(keyStr = outputSeparatorCommandName,
            helpStr = "--output-separator [STRING]\n" + "for CSV output: the separator in the output file (optional, defaults to \",\")"),

        new defaultcommand(keyStr = separatorCommandName,
            helpStr = "--separator [SEPARATOR]\nCommand line option to mention input file separator to read along"),

        new defaultcommand(keyStr = formatCommandName,
            helpStr = "--format [TYPE] (default csv)\nCommand line option to mention output file format where TYPE = csv or xml"),

        new defaultcommand(keyStr = rangeCommandName, inputCount = 2,
            helpStr = "--range [FROM] [TO]\nCommand line option to mention the range for printing the output table"),

        new togglecommand(keyStr = helpCommandName,
            helpStr = "--help\nCommand line option to show and explain all the options"),

        new repeatcommand(keyStr = filterCommandName, inputCount = 3,
            helpStr = "--filter [COLUMN] [OPERATOR] [VALUE]\n" +
                "filter out lines that fail inequality checks on cells in columns (optional, can be repeated)"),

        new repeatcommand(keyStr = filterEmptyCommandName,
            helpStr = "--filter-is-empty [COLUMN]\nfilter out lines with non-empty cells on column (optional, can be repeated)")
    )

}
