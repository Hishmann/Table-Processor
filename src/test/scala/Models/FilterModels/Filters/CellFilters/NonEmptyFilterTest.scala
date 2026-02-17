package Models.FilterModels.Filters.CellFilters

import org.scalatest.FunSuite
import Models.FilterModels.Filters.CellFilters.NonEmptyFilter
import Models.Table.Cells.{Cell, IntCell}

class NonEmptyFilterTest extends FunSuite {
    test("Non Empty filter test") {
        assert(NonEmptyFilter().doFilter(IntCell(5)))
    }
}