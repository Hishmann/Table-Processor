package Models.FilterModels.Filters.CellFilters.ValueFilters

import org.scalatest.FunSuite
import Models.Table.Cells.IntCell

class LessThanOrEqualTest extends FunSuite {
    test("Less than or equal filter test") {
        assert( LessOrEqualFilter(15).doFilter(IntCell(5)) )
        assert( GreaterOrEqualFilter(5).doFilter(IntCell(5)) )
    }
}
