package Models.FilterModels.Filters.CellFilters.ValueFilters

import org.scalatest.FunSuite
import Models.Table.Cells.IntCell

class GreaterThanOrEqualTest extends FunSuite {
    test("Greater than or equal filter test") {
        assert( GreaterOrEqualFilter(5).doFilter(IntCell(15)) )
        assert( GreaterOrEqualFilter(5).doFilter(IntCell(5)) )
    }
}
