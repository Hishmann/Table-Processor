package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Brackets

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Bracket

/**
 * A case class representing a closing bracket token, extending the `Bracket` trait.
 *
 * The `CloseBracket` class is used to represent closing brackets in expression parsing.
 * It specifies the type of the closing bracket (e.g., ')', '}', ']').
 *
 * @param bracketType A character representing the type of the closing bracket.
 *                    For example:
 *                    - ')' for closing parentheses.
 *                    - '}' for closing curly braces.
 *                    - ']' for closing square brackets.
 */
case class CloseBracket(bracketType: Char) extends Bracket
