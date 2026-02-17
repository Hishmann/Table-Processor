package TableProcessor.TableFilterNeeds.GetRowFilterList

// Import necessary models and utilities for command argument parsing and value-based filtering
import Models.CommandModels.CommandArgumentsCollection
import TableProcessor.CommandUtils.CommandUtils
import Models.FilterModels.Filters.CellFilters.ValueFilter
import Models.FilterModels.Filters.CellFilters.ValueFilters.{
    GreaterThanFilter, GreaterOrEqualFilter, LessOrEqualFilter, LessThanFilter, EqualFilter, NotEqualFilter
}
import Models.FilterModels.Table.TableRowFilterContext

/**
 * ValueGetRowFilterListParser parses command-line arguments for value-based filters
 * and generates the corresponding filter contexts.
 * @param args CommandArgumentsCollection containing parsed command-line arguments.
 */
class ValueGetRowFilterListParser(args: CommandArgumentsCollection) extends GetRowFilterListParser(args) {

    /**
     * Maps operators to their corresponding ValueFilter constructors.
     * Each operator string is associated with a function that creates a filter instance.
     */
    private val operationValueFilterMap: Map[String, Int => ValueFilter] = Map(
        ">" -> ((a: Int) => new GreaterThanFilter(a)), // Maps '>' operator to GreaterThanFilter
        ">=" -> ((a: Int) => new GreaterOrEqualFilter(a)), // Maps '>' operator to GreaterThanFilter
        "<" -> ((a: Int) => new LessThanFilter(a)), // Maps '>' operator to GreaterThanFilter
        "<=" -> ((a: Int) => new LessOrEqualFilter(a)), // Maps '>' operator to GreaterThanFilter
        "==" -> ((a: Int) => new EqualFilter(a)), // Maps '>' operator to GreaterThanFilter
        "!=" -> ((a: Int) => new NotEqualFilter(a)) // Maps '>' operator to GreaterThanFilter
    )

    /**
     * Parses command-line arguments to generate a list of TableRowFilterContext objects
     * for value-based filters applied to specific columns.
     * @return A list of TableRowFilterContext objects for filtering rows based on column values.
     * @throws RuntimeException if the filter arguments are invalid.
     */
    def getFilters: List[TableRowFilterContext] = {
        var allFilters: List[TableRowFilterContext] = List() // Initialize an empty list for filters

        // Regular expressions for validating column names, operators, and numeric values
        val lettersRegex = "^[A-Z]+$".r
        val numbersRegex = "^[0-9]+$".r
        val operatorsRegex = ("^(" + operationValueFilterMap.keys.mkString("|") + ")$").r

        // Extract arguments for the value filter command
        val valueFilters = args.argFromCommOrEmpty(CommandUtils.filterCommandName) match {
            case Some(value) => value.arguments // Use the arguments if present
            case _ => List() // Default to an empty list if no arguments are provided
        }

        // Process filters in groups of three (column, operator, value)
        if (valueFilters.nonEmpty) {
            for (i <- valueFilters.grouped(3).toList) {
                val col: String = i(0) // Column name
                val op = i(1)         // Operator
                val num = i(2)        // Numeric value

                // Validate column name, operator, and numeric value
                if (lettersRegex.matches(col) && operatorsRegex.matches(op) && numbersRegex.matches(num)) {
                    // Create a filter based on the operator and numeric value
                    val filter = operationValueFilterMap.get(op) match {
                        case Some(value) => value(num.toInt) // Apply the operator to create a filter
                        case None => throw new RuntimeException("Filter recognition error") // Error if operator is not recognized
                    }
                    // Add the filter context to the list
                    allFilters = allFilters :+ new TableRowFilterContext(col, filter)
                } else {
                    // Throw an exception for invalid filter arguments
                    throw new RuntimeException("Value Filter arguments are incorrect")
                }
            }
        }

        allFilters // Return the list of generated filters
    }
}
