package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaFormats

import org.scalatest.FunSuite

class ArithmeticTableFormulaFormatTest extends FunSuite {

    val formulaFormatCheck = new ArithmeticTableFormulaFormat()
    test("Valid formats for Table evaluator to have") {
        assert(formulaFormatCheck.validFormat("=A1+B2"))
        assert(formulaFormatCheck.validFormat("  =   A1  +  B2  "))
        assert(formulaFormatCheck.validFormat("=A1+B2/2"))
        assert(formulaFormatCheck.validFormat("=A1+(B2/2)"))

        assert(!formulaFormatCheck.validFormat("A1+B2"))
        assert(!formulaFormatCheck.validFormat("=A1+B2$"))
        assert(!formulaFormatCheck.validFormat("=A1+B2C1/2"))
        assert(!formulaFormatCheck.validFormat("=A1+2C/2"))
    }

}
