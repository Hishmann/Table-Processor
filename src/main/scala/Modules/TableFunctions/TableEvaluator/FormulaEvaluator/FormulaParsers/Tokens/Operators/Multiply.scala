package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operators

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operator

/**
 * A case class representing the multiplication operator, extending the `Operator` trait.
 *
 * The `Multiply` class implements the behavior of the multiplication operator, including its precedence,
 * associativity, and the operation it performs on two operands.
 */
case class Multiply() extends Operator {

    /**
     * Returns the precedence level of the multiplication operator.
     *
     * @return `2`, representing the precedence level of multiplication,
     *         which is higher than addition and subtraction but equal to division.
     */
    override def precedence: Int = 2

    /**
     * Indicates whether the multiplication operator is right-associative.
     *
     * @return `false`, as multiplication is left-associative.
     */
    override def isRightAssociative: Boolean = false

    /**
     * Performs the multiplication operation on the given operands.
     *
     * @param left The left operand as an integer.
     * @param right An optional right operand as an integer.
     *              The `Multiply` operator requires both operands, so `right` must be defined.
     * @return The result of multiplying `left` by `right`.
     * @throws NoSuchElementException if `right` is `None`.
     */
    override def operate(left: Int, right: Option[Int]): Int = left * right.get
}
