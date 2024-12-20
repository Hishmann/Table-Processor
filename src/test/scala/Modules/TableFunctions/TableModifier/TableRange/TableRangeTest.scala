package Modules.TableFunctions.TableModifier.TableRange

import org.scalatest.FunSuite
import Modules.TableFunctions.TableCreator.TableCreatorFromText
import Models.Table.{Table, TextTable}
import Models.RangeModels.RangeContext

class TableRangeTest extends FunSuite {
    val TR = new TableRange()

    val test1: Table = new TableCreatorFromText().getTable(TextTable(
        List(
        List("10", "1", "20"),
        List("20", "2", ""),
        List("400", "", "2"),
        List("500", "1", "2")
    )))

    test("Range working") {
        val testFirst = TR.modify(test1, new RangeContext("B1","C2"))
        assert(testFirst.getRows == List(0,1))
        assert(testFirst.getColumns == List(1,2))

        val testFirst2 = TR.modify(test1, new RangeContext("C2","B1"))
        assert(testFirst2.getRows == List(0,1))
        assert(testFirst2.getColumns == List(1,2))

        val testFirst3 = TR.modify(test1, new RangeContext("B2","C1"))
        assert(testFirst3.getRows == List(0,1))
        assert(testFirst3.getColumns == List(1,2))

        val testFirst4 = TR.modify(test1, new RangeContext("C1","B2"))
        assert(testFirst4.getRows == List(0,1))
        assert(testFirst4.getColumns == List(1,2))

        val testSecond = TR.modify(test1, new RangeContext("B1","B1"))
        assert(testSecond.getRows == List(0))
        assert(testSecond.getColumns == List(1))
    }

    test("Invalid Range context") {
        assertThrows[Exception] {
            var testF = TR.modify(test1, new RangeContext("2","BB"))
        }

    }
}
