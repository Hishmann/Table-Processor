package Models.FilterModels.Table

import Models.FilterModels.Filters.CellFilter

/**
 * A class that represents the context for filtering rows in a table based on a specific column and filter.
 *
 * This class associates a column name with a `CellFilter` instance to define filtering criteria
 * for a table row. The filter will be applied to cells in the specified column.
 *
 * @param column The name of the column to apply the filter to.
 * @param filter An instance of `CellFilter` that defines the filtering logic for the column's cells.
 */
case class TableRowFilterContext(val column: String, val filter: CellFilter) {
}
