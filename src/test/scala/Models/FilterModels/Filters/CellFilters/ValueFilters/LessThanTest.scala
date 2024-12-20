package Models.FilterModels.Filters.CellFilters.ValueFilters

import org.scalatest.FunSuite
import Models.Table.Cells.IntCell

class LessThanTest extends FunSuite {
    test("Less than filter test") {
        assert( LessThanFilter(15).doFilter(IntCell(5)) )
    }
}
