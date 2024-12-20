package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operators

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operator

/**
 * A case class representing the exponentiation operator, extending the `Operator` trait.
 *
 * The `Exponent` class implements the behavior of the exponentiation operator, including its precedence,
 * associativity, and the operation it performs on two operands.
 */
case class Exponent() extends Operator {

    /**
     * Returns the precedence level of the exponentiation operator.
     *
     * @return `3`, representing the precedence level of exponentiation, which is higher than multiplication, division, addition, or subtraction.
     */
    override def precedence: Int = 3

    /**
     * Indicates whether the exponentiation operator is right-associative.
     *
     * @return `true`, as exponentiation is right-associative (e.g., `a^b^c` is evaluated as `a^(b^c)`).
     */
    override def isRightAssociative: Boolean = true

    /**
     * Performs the exponentiation operation on the given operands.
     *
     * @param left The base as an integer.
     * @param right An optional exponent as an integer.
     *              The `Exponent` operator requires both operands, so `right` must be defined.
     * @return The result of raising `left` to the power of `right`.
     * @throws NoSuchElementException if `right` is `None`.
     */
    override def operate(left: Int, right: Option[Int]): Int = Math.pow(left, right.get).toInt
}

