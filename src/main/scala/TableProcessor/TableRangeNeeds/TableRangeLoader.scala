package TableProcessor.TableRangeNeeds

// Import necessary module for table range modification
import Modules.TableFunctions.TableModifier.TableRange.TableRange

/**
 * TableRangeLoader provides an instance of the TableRange modifier.
 * This is used to apply range-based modifications to a table.
 */
class TableRangeLoader() {

    /**
     * Returns an instance of TableRange.
     * This instance is used to modify the table based on the specified or default range.
     *
     * @return A TableRange instance for handling table range modifications.
     */
    def getLoader: TableRange = {
        new TableRange // Instantiate and return a new TableRange
    }
}
