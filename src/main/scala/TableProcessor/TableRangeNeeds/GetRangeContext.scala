package TableProcessor.TableRangeNeeds

// Import necessary models and utilities for handling ranges and tables
import Models.CommandModels.CommandArgumentsCollection
import TableProcessor.CommandUtils.CommandUtils
import Models.RangeModels.RangeContext
import Models.Table.Table
import Modules.TableFunctions.ReferencingIndices.indexToColumn

/**
 * GetRangeContext is responsible for parsing the range arguments from the command-line
 * or determining a default range based on the provided table.
 * @param args CommandArgumentsCollection containing parsed command-line arguments.
 */
class GetRangeContext(args: CommandArgumentsCollection) {

    /**
     * Determines the range context for the table.
     * If the range is specified in the command-line arguments, it uses those values.
     * Otherwise, it calculates the default range spanning the entire table.
     *
     * @param table The table whose range context is being determined.
     * @return A RangeContext object representing the specified or default range.
     */
    def getRange(table: Table): RangeContext = {
        args.argFromCommOrEmpty(CommandUtils.rangeCommandName) match {
            case Some(value) =>
                // Create a RangeContext from the specified range arguments
                RangeContext(value.arguments(0), value.arguments(1))
            case None =>
                // Calculate the default range (entire table)
                RangeContext(
                    "A1", // Starting from the top-left corner
                    indexToColumn(table.getRows.last) + (table.getColumns.last + 1).toString // Ending at bottom-right corner
                )
        }
    }
}
