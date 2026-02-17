package Models.FilterModels.Filters.CellFilters

import org.scalatest.FunSuite
import Models.FilterModels.Filters.CellFilters.EmptyFilter
import Models.Table.Cells.EmptyCell

class EmptyFilterTest extends FunSuite {
    test("Empty filter test") {
        assert(EmptyFilter().doFilter(new EmptyCell))
    }
}
