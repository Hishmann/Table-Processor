package Models.FilterModels.Filters.CellFilters.ValueFilters
import org.scalatest.FunSuite
import Models.Table.Cells.IntCell

class GreaterThanTest extends FunSuite {
    test("Greater than filter test") {
        assert( GreaterThanFilter(5).doFilter(IntCell(15)) )
    }
}
