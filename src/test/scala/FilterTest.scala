import org.scalatest.FunSuite
import Table.TableModifiers.{FilterApplied, ColAndFilter}
import Table.TableModifiers.Filters.filterInputHelpers._
import Table.TableModifiers.Filters._
import Table.Table
import Table.TableModifiers.TableFilter

class FilterTest extends FunSuite {
    test("Filter Formats Working") {
        assert(isCellFilterFormat(List("AA")))
        assert(!isCellFilterFormat(List("1")))
        assert(isValueFilterFormat(List("AB", ">", "10")))
        assert(!isValueFilterFormat(List("AB", ")", "0")))
    }

    val FA = new FilterApplied
    val output1: List[ColAndFilter] = List(
        new ColAndFilter("A", new emptyFilter),
        new ColAndFilter("B", new greaterThanFilter(10))
    )
    test("Filter Applied Working") {
        FA.addCellFilterToApply("A", "empty")
        FA.addValueFilterToApply("B", ">", "10")
        for (i <- output1.indices) {
            assert(FA.getFilterSetting(i).column == output1(i).column &&
                  FA.getFilterSetting(i).filter == output1(i).filter)
        }
    }

    test("Table Filtering 1") {
        val testTable = new Table(List(
            List("", "10", "10"),
            List("10", "15", "10"),
            List("10", "10", "10"),
            List("10", "16", "10")
        ))
        val tableFilterer = new TableFilter(FA)
        assert(tableFilterer.filteredTable(testTable).getRows == List(2))
    }

    test("Table Filtering taking unevaluated inputs") {
        val testTable = new Table(List(
            List("", "10", "10"),
            List("10", "=A2+15", "10"),
            List("10", "10", "10"),
            List("10", "16", "10")
        ))
        val tableFilterer = new TableFilter(FA)
        assertThrows[RuntimeException](
            tableFilterer.filteredTable(testTable)
        )
    }

}
