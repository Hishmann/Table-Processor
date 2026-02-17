package Modules.TableFunctions.TableModifier.TableRowFilter

import org.scalatest.FunSuite
import Modules.TableFunctions.TableCreator.TableCreatorFromText
import Models.Table.{Table, TextTable}

import Models.FilterModels.Filters.CellFilters.{EmptyFilter, NonEmptyFilter}
import Models.FilterModels.Filters.CellFilters.ValueFilters.{GreaterOrEqualFilter, EqualFilter}
import Models.FilterModels.Table.TableRowFilterContext

class TableRowFilterTest extends FunSuite {
    val TRF = new TableRowFilter()

    val test1: Table = new TableCreatorFromText().getTable(TextTable(
        List(
        List("10", "1", "20"),
        List("20", "2", ""),
        List("400", "", "2"),
        List("500", "1", "2")
    )))

    test("Collection of Filters in a List") {
        var test = TRF.modify(test1, new TableRowFilterContext("B", EmptyFilter()))
        test = TRF.modify(test, new TableRowFilterContext("A", GreaterOrEqualFilter(500)))
        test = TRF.modify(test, new TableRowFilterContext("C", NonEmptyFilter()))
        assert(test.getRows == List(1))
    }

    test("Invalid column in context") {
        assertThrows[Exception] {
            var test = TRF.modify(test1, new TableRowFilterContext("54", EmptyFilter()))
        }
    }

}
