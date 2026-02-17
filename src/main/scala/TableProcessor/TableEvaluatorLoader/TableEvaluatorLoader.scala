package TableProcessor.TableEvaluatorLoader

// Import necessary modules for table evaluation and formula handling
import Modules.TableFunctions.TableEvaluator.TableEvaluator
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.TableFormulaEvaluator
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.ArithmeticFormulaParser
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaFormats.ArithmeticTableFormulaFormat

/**
 * TableEvaluatorLoader is responsible for providing an instance of a TableEvaluator.
 * It enables the evaluation of table data using formulas, such as arithmetic operations.
 */
class TableEvaluatorLoader {

    /**
     * Returns an instance of TableEvaluator configured for arithmetic formula evaluation.
     * @return A TableEvaluator instance for processing table data with arithmetic formulas.
     */
    def getEvaluator: TableEvaluator = {
        new TableFormulaEvaluator(
            formulaFormat = new ArithmeticTableFormulaFormat, // Format for arithmetic formulas
            parser = new ArithmeticFormulaParser             // Parser for interpreting arithmetic formulas
        )
    }
}

