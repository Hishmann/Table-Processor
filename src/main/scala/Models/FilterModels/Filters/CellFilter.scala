package Models.FilterModels.Filters

import Models.FilterModels.Filter
import Models.Table.Cells.Cell

/**
 * A trait that extends the generic `Filter` trait, specifically for filtering `Cell` objects.
 *
 * This trait provides a specialized filter for `Cell` objects, where the filtering logic
 * operates on instances of `Cell[_]`.
 *
 * @see Filter
 */
trait CellFilter extends Filter[Cell[_]] {
}
