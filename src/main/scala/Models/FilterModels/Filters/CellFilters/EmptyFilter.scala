package Models.FilterModels.Filters.CellFilters

import Models.FilterModels.Filters.CellFilter
import Models.Table.Cells.{Cell, EmptyCell}

/**
 * A case class that implements `CellFilter` to filter for empty cells.
 *
 * The `EmptyFilter` class overrides the `doFilter` method to check if a given `Cell[_]`
 * is an instance of `EmptyCell`. If the cell is empty, the filter returns `true`;
 * otherwise, it returns `false`.
 */
case class EmptyFilter() extends CellFilter {

    /**
     * Filters the input to determine if it is an `EmptyCell`.
     *
     * @param input The `Cell[_]` instance to be evaluated.
     * @return `true` if the input is an `EmptyCell`, `false` otherwise.
     */
    override def doFilter(input: Cell[_]): Boolean = input match {
        case cell: EmptyCell => true // Return true if the input is an instance of `EmptyCell`.
        case _ => false             // Return false for all other types of cells.
    }
}