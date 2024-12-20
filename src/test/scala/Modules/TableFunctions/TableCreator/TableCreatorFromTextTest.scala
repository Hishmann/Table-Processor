package Modules.TableFunctions.TableCreator

import org.scalatest.FunSuite
import Models.Table.TextTable
import Models.Table.Table
import Models.Table.Cells.{IntCell, EmptyCell, FormulaCell}

class TableCreatorFromTextTest extends FunSuite {

    val TCtoTT = new TableCreatorFromText()

    val test1: TextTable = TextTable(List(
        List("1","=A1","3"),
        List("1","","=A1+B2"),
    ))
    val result1: Table = Table()
    result1.setData(List(
        List(IntCell(1),FormulaCell("=A1"),IntCell(3)),
        List(IntCell(1),EmptyCell(),FormulaCell("=A1+B2"))
    ))
    test("Table Creator creating valid Table") {
        assert(TCtoTT.getTable(test1).equals(result1))
    }

    val test2: TextTable = TextTable(List(
        List("1","=A1","3"),
        List("1","2"),
        List("1","","3"),
    ))
    test("Invalid table error due to non rectangular shape") {
        assertThrows[RuntimeException] {
            TCtoTT.getTable(test2)
        }
    }
}
