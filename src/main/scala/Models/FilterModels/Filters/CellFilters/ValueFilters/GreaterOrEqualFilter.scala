package Models.FilterModels.Filters.CellFilters.ValueFilters

import Models.FilterModels.Filters.CellFilters.ValueFilter
import Models.Table.Cells.{Cell, IntCell}

/**
 * A case class that filters `Cell` objects whose content is greater or equal than a specified value.
 *
 * This class extends the `ValueFilter` abstract class, specializing it to filter `IntCell`
 * instances where the integer content is greater than the provided value.
 *
 * @param value The threshold value for filtering.
 */
case class GreaterOrEqualFilter(override val value: Int) extends ValueFilter(value) {

    /**
     * Filters the input to determine if it is an `IntCell` with content greater or equal than the threshold value.
     *
     * @param input The `Cell[_]` instance to be evaluated.
     * @return `true` if the input is an `IntCell` with content greater or equal than `value`,
     *         `false` otherwise.
     */
    override def doFilter(input: Cell[_]): Boolean = input match {
        case cell: IntCell => cell.cellContent >= value // Check if the content of the `IntCell` is greater than the threshold.
        case _ => false                               // Return false for all other types of cells.
    }
}
