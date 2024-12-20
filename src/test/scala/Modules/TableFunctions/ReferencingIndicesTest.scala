package Modules.TableFunctions

import Models.Table.{TextTable, Table}
import org.scalatest.FunSuite
import Modules.TableFunctions.TableCreator.TableCreatorFromText

class ReferencingIndicesTest extends FunSuite {
    test("Column to Index") {
        assert(ReferencingIndices.columnToIndex("E") == 4)
        assert(ReferencingIndices.columnToIndex("AB") == 27)
        assertThrows[Exception] {
            ReferencingIndices.columnToIndex("2AB")
        }
    }
    test("Index to Column") {
        assert(ReferencingIndices.indexToColumn(4) == "E")
        assert(ReferencingIndices.indexToColumn(26) == "AA")
    }

    val test1: TextTable = new TextTable(List(
        List("1", "4", "020"),
        List("10000", "=C1+10", "=(6/2)*(1+2)"),
        List("2", "", "=A1+B2+(-10*6/2)")
    ))
    val table1: Table = new TableCreatorFromText().getTable(test1)

    test("Table Referencing") {
        assert(ReferencingIndices.tableIndexReturn("C2", table1) == table1.getData(1)(2))
    }
}
