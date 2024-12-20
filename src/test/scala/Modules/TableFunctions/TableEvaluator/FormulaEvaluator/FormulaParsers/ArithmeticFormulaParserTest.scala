package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers

import org.scalatest.FunSuite

class ArithmeticFormulaParserTest extends FunSuite {

    def evaluate(expression: String): Int = {
        val parser = new ArithmeticFormulaParser
        parser.evaluate(expression)
    }

    test("Parser Test") {
        assert(evaluate("5 - 5 + 5") == 5 )
        assert(evaluate("=5") == 5 )
        assert(evaluate("=(5 / 5) * 3 +2+1") == 6)
        assert(evaluate("=(5 / 5) * (3 + 2 + 1)") == 6)
    }

    test("Invalid Expressions") {
        assertThrows[Exception] {
            evaluate("5 - 5// + 5")
        }
        assertThrows[Exception] {
            evaluate("5 $ 5")
        }
        assertThrows[Exception] {
            evaluate("==5 + 5")
        }
    }
}
