package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operators

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operator

/**
 * A case class representing the unary minus operator, extending the `Operator` trait.
 *
 * The `UnaryMinus` class implements the behavior of the unary minus operator, which negates a single operand.
 * This operator has a higher precedence than binary operators like addition or subtraction.
 */
case class UnaryMinus() extends Operator {

    /**
     * Returns the precedence level of the unary minus operator.
     *
     * @return `4`, representing a higher precedence level than binary operators.
     */
    override def precedence: Int = 4

    /**
     * Indicates whether the unary minus operator is right-associative.
     *
     * @return `true`, as unary operators are typically right-associative.
     */
    override def isRightAssociative: Boolean = true

    /**
     * Performs the unary negation operation on the given operand.
     *
     * @param left The operand to be negated as an integer.
     * @param right An optional right operand, which is not used for unary operators.
     * @return The negated value of `left`.
     */
    override def operate(left: Int, right: Option[Int]): Int = -left
}
