package TableProcessor.TableFilterNeeds.GetRowFilterList

// Import necessary models and utilities for command argument parsing and filter creation
import Models.CommandModels.CommandArgumentsCollection
import TableProcessor.CommandUtils.CommandUtils
import Models.FilterModels.Filters.CellFilters.{EmptyFilter, NonEmptyFilter}
import Models.FilterModels.Table.TableRowFilterContext

/**
 * EmptyGetRowFilterListParser is responsible for parsing command-line arguments
 * related to "empty" row filters and generating the corresponding filter contexts.
 * @param args CommandArgumentsCollection containing parsed command-line arguments.
 */
class EmptyGetRowFilterListParser(args: CommandArgumentsCollection) extends GetRowFilterListParser(args) {

    /**
     * Parses the command-line arguments to generate a list of TableRowFilterContext
     * for "empty" cell filters based on specified columns.
     * @return A list of TableRowFilterContext objects for filtering rows with empty cells.
     * @throws RuntimeException if the filter arguments are invalid.
     */
    def getFilters: List[TableRowFilterContext] = {
        var allFilters: List[TableRowFilterContext] = List() // Initialize an empty list for filters

        // Regular expression to validate column identifiers (e.g., A, B, AA)
        val lettersRegex = "^[A-Z]+$".r

        // Extract arguments for the "empty" filter command
        val emptyFilter = args.argFromCommOrEmpty(CommandUtils.filterEmptyCommandName) match {
            case Some(value) => value.arguments // Use the arguments if present
            case _ => List() // Default to an empty list if no arguments are provided
        }
        // Extract arguments for the "non-empty" filter command
        val NotEmptyFilter = args.argFromCommOrEmpty(CommandUtils.filterNotEmptyCommandName) match {
            case Some(value) => value.arguments // Use the arguments if present
            case _ => List() // Default to an empty list if no arguments are provided
        }

        // Process each column specified for the "empty" filter
        if (emptyFilter.nonEmpty) {
            for (i <- emptyFilter) {
                val col = i
                // Validate the column identifier using the regular expression
                if (lettersRegex.matches(col)) {
                    // Add a new TableRowFilterContext for the specified column with an EmptyFilter
                    allFilters = allFilters :+ new TableRowFilterContext(col, new EmptyFilter)
                } else {
                    // Throw an exception for invalid column identifiers
                    throw new RuntimeException("Empty Filter arguments are incorrect")
                }
            }
        }
        // Process each column specified for the "non-empty" filter
        if (NotEmptyFilter.nonEmpty) {
            for (i <- NotEmptyFilter) {
                val col = i
                // Validate the column identifier using the regular expression
                if (lettersRegex.matches(col)) {
                    // Add a new TableRowFilterContext for the specified column with an NonEmptyFilter
                    allFilters = allFilters :+ new TableRowFilterContext(col, new NonEmptyFilter)
                } else {
                    // Throw an exception for invalid column identifiers
                    throw new RuntimeException("Empty Filter arguments are incorrect")
                }
            }
        }
        allFilters // Return the list of generated filters
    }
}

