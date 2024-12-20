package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operators

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operator

/**
 * A case class representing the addition operator, extending the `Operator` trait.
 *
 * The `Add` class implements the behavior of the addition operator, including its precedence,
 * associativity, and the operation it performs on two operands.
 */
case class Add() extends Operator {

    /**
     * Returns the precedence level of the addition operator.
     *
     * @return `1`, representing the precedence level of addition.
     */
    override def precedence: Int = 1

    /**
     * Indicates whether the addition operator is right-associative.
     *
     * @return `false`, as addition is left-associative.
     */
    override def isRightAssociative: Boolean = false

    /**
     * Performs the addition operation on the given operands.
     *
     * @param left The left operand as an integer.
     * @param right An optional right operand as an integer.
     *              The `Add` operator requires both operands, so `right` must be defined.
     * @return The result of adding `left` and `right`.
     * @throws NoSuchElementException if `right` is `None`.
     */
    override def operate(left: Int, right: Option[Int]): Int = left + right.get
}
