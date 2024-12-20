package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens

/**
 * A case class representing an operand token, extending the `Token` trait.
 *
 * The `Operand` class is used to encapsulate integer values as tokens in contexts
 * such as parsing or evaluating expressions.
 *
 * @param value The integer value of the operand.
 */
case class Operand(value: Int) extends Token
