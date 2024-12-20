package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaFormats

/**
 * A trait for validating the format of a formula or expression.
 *
 * The `FormulaFormat` trait defines a contract for checking whether a given
 * formula or expression adheres to a valid format.
 */
trait FormulaFormat {

    /**
     * Validates the format of the given expression.
     *
     * @param expression A string representing the formula or expression to be validated.
     * @return `true` if the expression is in a valid format, `false` otherwise.
     */
    def validFormat(expression: String): Boolean
}

