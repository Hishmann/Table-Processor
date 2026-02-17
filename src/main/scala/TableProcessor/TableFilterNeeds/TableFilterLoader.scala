package TableProcessor.TableFilterNeeds

// Import necessary modules and models for table filtering
import Models.FilterModels.Table.TableRowFilterContext
import Modules.TableFunctions.TableModifier.TableModifier
import Modules.TableFunctions.TableModifier.TableRowFilter.TableRowFilter

/**
 * TableFilterLoader provides an instance of a TableModifier configured for row filtering.
 * It supports applying filters to modify table rows based on specific conditions.
 */
class TableFilterLoader {

    /**
     * Returns an instance of TableModifier specialized for filtering table rows.
     * @return A TableModifier[TableRowFilterContext] instance for applying row-level filters.
     */
    def getLoader: TableModifier[TableRowFilterContext] = {
        new TableRowFilter() // Instantiate and return a TableRowFilter
    }
}
