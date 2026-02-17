package Modules.TableFunctions.TablePrinter.TablePrinterText

import Models.Table.{Table, TextTable}
import Modules.TableFunctions.TableCreator.TableCreatorFromText
import org.scalatest.FunSuite

class TablePrinterMDTextTest extends FunSuite {
    val TP = new TablePrinterMDText()

    val test1: Table = new TableCreatorFromText().getTable(
        new TextTable(
            List(
                List("1", "4", "020"),
                List("10000", "30", "9"),
                List("2", "", "1")
            ))
    )
    val result1 = "|       |    |    |\n|-------|----|----|\n|     1 |  4 | 20 |\n| 10000 | 30 |  9 |\n|     2 |    |  1 |"
    test("Basic function") {
        assert(TP.print(test1).trim == result1.trim)
    }
}
