package Models.FilterModels.Filters.CellFilters.ValueFilters

import Models.Table.Cells.IntCell
import org.scalatest.FunSuite

class NotEqualTest extends FunSuite {
    test("Not equal filter test") {
        assert( NotEqualFilter(15).doFilter(IntCell(5)) )
    }
}