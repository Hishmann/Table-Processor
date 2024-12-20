package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers

/**
 * A trait for parsing and evaluating formulas, producing a result of type `V`.
 *
 * The `FormulaParser` trait defines a contract for interpreting and evaluating formulas.
 * It provides a method to compute the result while handling potential exceptions during the process.
 *
 * @tparam V The type of the value produced by the evaluation.
 */
trait FormulaParser[V] {

    /**
     * Evaluates the formula and returns the computed result.
     *
     * @param exception A string representing an exception or error message to handle during evaluation.
     * @return The result of the formula evaluation as an instance of type `V`.
     *         The implementation should define how to handle exceptions and provide the result.
     */
    def evaluate(exception: String): V
}
