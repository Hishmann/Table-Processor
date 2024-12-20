package Modules.TableFunctions.TableEvaluator.FormulaEvaluator

import org.scalatest.FunSuite
import Models.Table.TextTable
import Models.Table.Table
import Modules.TableFunctions.TableCreator.TableCreatorFromText
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.ArithmeticFormulaParser
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaFormats.ArithmeticTableFormulaFormat

class TableFormulaEvaluatorTest  extends  FunSuite {
    val test1: TextTable = new TextTable(List(
        List("1", "4", "020"),
        List("10000", "=C1+10", "=(6/2)*(1+2)"),
        List("2", "", "=A1+B2+(-10*6/2)")
    ))

    val TBC = new TableCreatorFromText()
    val TableEval = new TableFormulaEvaluator(
        new ArithmeticTableFormulaFormat,
        new ArithmeticFormulaParser
    )

    test("Formula Evaluater working") {
        val Table = TBC.getTable(test1)
        val TableRes = TableEval.evaluate(Table)
        assert(TableRes.getData == TBC.getTable(
                new TextTable(
                    List(
                    List("1", "4", "020"),
                    List("10000", "30", "9"),
                    List("2", "", "1")
                ))
            ).getData
        )
    }

    val test2: TextTable = new TextTable(List(
        List("1", ""),
        List("10000", "=B1+10")
    ))

    test("Catches Empty cell References") {
        val Table = TBC.getTable(test2)
        assertThrows[RuntimeException] {
            val TableRes = TableEval.evaluate(Table)
        }
    }

    val test3: TextTable = new TextTable(List(
        List("1", "5"),
        List("10000", "=B1+@10")
    ))

    test("Catches Invalid Formulas with wrong formatting or unknown symbols") {
        assertThrows[RuntimeException] {
            val Table = TBC.getTable(test3)
            val TableRes = TableEval.evaluate(Table)
        }
    }

    val test4: TextTable =  new TextTable(List(
        List("1", "5"),
        List("10000", "B1+10")
    ))

    test("Catches Invalid Formulas without a equal sign") {
        assertThrows[RuntimeException] {
            val Table = TBC.getTable(test4)
            val TableRes = TableEval.evaluate(Table)
        }
    }

    val test5: TextTable =  new TextTable(List(
        List("=A2", "5", "=B2"),
        List("=A3", "=C3", "=10"),
        List("100", "10", "=A1+C1")
    ))

    test("Testing Cyclical dependencies") {
        val Table = TBC.getTable(test5)
        assertThrows[RuntimeException] {
            val TableRes = TableEval.evaluate(Table)
        }
    }
}
