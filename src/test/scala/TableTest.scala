import org.scalatest.FunSuite
import TableCreator.TableCreatorFromListStrings
import TableEvaluator.FormulaEvaluator.TableFormulaEvaluator

class TableTest extends FunSuite {
    val test1: List[List[String]] = List(
        List("1", "4", "020"),
        List("10000", "=C1+10", "=6/2*(1+2)"),
        List("2", "", "=A1+B2+(-10*6/2)")
    )

    val TBC = new TableCreatorFromListStrings()
    val TableEval = new TableFormulaEvaluator()

    test("Formula Evaluater working") {
        val Table = TBC.dataInitializer(test1)
        val TableRes = TableEval.evaluate(Table)
        assert(TableRes.getData == TBC.dataInitializer(
                List(
                    List("1", "4", "020"),
                    List("10000", "30", "1"),
                    List("2", "", "1")
                )
            ).getData
        )
    }

    val test2: List[List[String]] = List(
        List("1", ""),
        List("10000", "=B1+10")
    )

    test("Catches Empty cell References") {
        val Table = TBC.dataInitializer(test2)
        assertThrows[RuntimeException] {
            val TableRes = TableEval.evaluate(Table)
        }
    }

    val test3: List[List[String]] = List(
        List("1", "5"),
        List("10000", "=B1+@10")
    )

    test("Catches Invalid Formulas with wrong formatting or unknown symbols") {
        assertThrows[RuntimeException] {
            val Table = TBC.dataInitializer(test3)
        }
    }

    val test4: List[List[String]] = List(
        List("1", "5"),
        List("10000", "B1+10")
    )

    test("Catches Invalid Formulas without a equal sign") {
        assertThrows[RuntimeException] {
            val Table = TBC.dataInitializer(test4)
        }
    }

    val test5: List[List[String]] = List(
        List("=A2", "5", "=B2"),
        List("=A3", "=C3", "=10"),
        List("100", "10", "=A1+C1")
    )

    test("Testing Cyclical dependencies") {
        val Table = TBC.dataInitializer(test5)
        assertThrows[RuntimeException] {
            val TableRes = TableEval.evaluate(Table)
        }
    }
}

