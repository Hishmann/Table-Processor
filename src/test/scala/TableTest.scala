import Table.Table
import Table.TableFormulaEvaluator.TableEvaluator
import org.scalatest.FunSuite

class TableTest extends FunSuite {
    val table1: List[List[String]] = List(
        List("1", "4", "020"),
        List("10000", "=C1+10", "=6/2*(1+2)"),
        List("2", "", "=A1+B2+(-10*6/2)")
    )

    test("Formula Evaluater working") {
        val Table = new Table(table1)
        val TableEval = new TableEvaluator(Table)
        val newTable = TableEval.evaluateTable()

        assert(newTable.isEqual(new Table(List(
            List("1", "4", "020"),
            List("10000", "30", "1"),
            List("2", "", "1")
        ))))
    }

    val table2: List[List[String]] = List(
        List("1", ""),
        List("10000", "=B1+10")
    )

    test("Catches Empty cell References") {
        val Table = new Table(table2)
        val TableEval = new TableEvaluator(Table)
        assertThrows[RuntimeException] {
            TableEval.evaluateTable()
        }
    }

    val table3: List[List[String]] = List(
        List("1", "5"),
        List("10000", "=B1+@10")
    )

    test("Catches Invalid Formulas with wrong formatting or unknown symbols") {
        assertThrows[RuntimeException] {
            val Table = new Table(table3)
        }
    }

    val table4: List[List[String]] = List(
        List("1", "5"),
        List("10000", "B1+10")
    )

    test("Catches Invalid Formulas without a equal sign") {
        assertThrows[RuntimeException] {
            val Table = new Table(table4)
        }
    }

    val table5: List[List[String]] = List(
        List("=A2", "5", "=B2"),
        List("=A3", "=C3", "=10"),
        List("100", "10", "=A1+C1")
    )

    test("Testing Cyclical dependencies") {
        val Table = new Table(table5)
        val TableEval = new TableEvaluator(Table)
        assertThrows[RuntimeException] {
            TableEval.evaluateTable()
        }
    }
}

