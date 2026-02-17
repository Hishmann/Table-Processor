package Models.FilterModels.Filters.CellFilters.ValueFilters

import Models.Table.Cells.IntCell
import org.scalatest.FunSuite

class EqualTest extends FunSuite {
    test("Equal filter test") {
        assert( EqualFilter(5).doFilter(IntCell(5)) )
    }
}
