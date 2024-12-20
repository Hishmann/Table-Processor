package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operators

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operator

/**
 * A case class representing the division operator, extending the `Operator` trait.
 *
 * The `Divide` class implements the behavior of the division operator, including its precedence,
 * associativity, and the operation it performs on two operands.
 */
case class Divide() extends Operator {

    /**
     * Returns the precedence level of the division operator.
     *
     * @return `2`, representing the precedence level of division, which is higher than addition or subtraction.
     */
    override def precedence: Int = 2

    /**
     * Indicates whether the division operator is right-associative.
     *
     * @return `false`, as division is left-associative.
     */
    override def isRightAssociative: Boolean = false

    /**
     * Performs the division operation on the given operands.
     *
     * @param left The left operand as an integer.
     * @param right An optional right operand as an integer.
     *              The `Divide` operator requires both operands, so `right` must be defined.
     * @return The result of dividing `left` by `right`.
     * @throws NoSuchElementException if `right` is `None`.
     * @throws ArithmeticException if `right` is `0`, as division by zero is undefined.
     */
    override def operate(left: Int, right: Option[Int]): Int = left / right.get
}

