package Models.FilterModels.Filters.CellFilters

import Models.FilterModels.Filters.CellFilter

/**
 * An abstract class that represents a filter for `Cell` objects based on an integer value.
 *
 * This class extends `CellFilter`, providing a foundation for filtering `Cell` objects
 * with a specific integer value as a criterion. Subclasses must implement the filtering
 * logic using the `doFilter` method from `CellFilter`.
 *
 * @param value An integer value used as a basis for filtering `Cell` objects.
 */
abstract class ValueFilter(val value: Int) extends CellFilter {
}
