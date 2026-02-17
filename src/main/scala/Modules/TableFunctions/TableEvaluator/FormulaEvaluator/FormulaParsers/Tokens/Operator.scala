package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens

/**
 * A trait representing an operator token, extending the `Token` trait.
 *
 * The `Operator` trait defines the properties and behavior of operators, such as
 * precedence, associativity, and the operation they perform on operands. Operators
 * can be used in contexts like mathematical or logical computations.
 */
trait Operator extends Token {
    /**
     * The precedence of the operator.
     *
     * @return An integer representing the precedence level of the operator.
     *         Higher values indicate higher precedence.
     */
    def precedence: Int
    /**
     * Determines if the operator is right-associative.
     *
     * @return `true` if the operator is right-associative, `false` otherwise.
     */
    def isRightAssociative: Boolean
    /**
     * Performs the operation represented by the operator.
     *
     * @param left The left operand as an integer.
     * @param right The optional right operand as an integer. Some operators (e.g., unary operators) may not require a right operand.
     * @return The result of applying the operator to the given operands as an integer.
     */
    def operate(left: Int, right: Option[Int]): Int
}
