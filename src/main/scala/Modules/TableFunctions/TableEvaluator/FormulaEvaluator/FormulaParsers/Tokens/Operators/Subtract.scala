package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operators

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operator

/**
 * A case class representing the subtraction operator, extending the `Operator` trait.
 *
 * The `Subtract` class implements the behavior of the subtraction operator, including its precedence,
 * associativity, and the operation it performs on two operands.
 */
case class Subtract() extends Operator {

    /**
     * Returns the precedence level of the subtraction operator.
     *
     * @return `1`, representing the precedence level of subtraction,
     *         which is equal to addition and lower than multiplication or division.
     */
    override def precedence: Int = 1

    /**
     * Indicates whether the subtraction operator is right-associative.
     *
     * @return `false`, as subtraction is left-associative.
     */
    override def isRightAssociative: Boolean = false

    /**
     * Performs the subtraction operation on the given operands.
     *
     * @param left The left operand as an integer.
     * @param right An optional right operand as an integer.
     *              The `Subtract` operator requires both operands, so `right` must be defined.
     * @return The result of subtracting `right` from `left`.
     * @throws NoSuchElementException if `right` is `None`.
     */
    override def operate(left: Int, right: Option[Int]): Int = left - right.get
}
