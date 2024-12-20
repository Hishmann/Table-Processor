package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Brackets

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Bracket

/**
 * A case class representing an opening bracket token, extending the `Bracket` trait.
 *
 * The `OpenBracket` class is used to represent opening brackets in expression parsing.
 * It specifies the type of the opening bracket (e.g., '(', '{', '[').
 *
 * @param bracketType A character representing the type of the opening bracket.
 *                    For example:
 *                    - '(' for opening parentheses.
 *                    - '{' for opening curly braces.
 *                    - '[' for opening square brackets.
 */
case class OpenBracket(bracketType: Char) extends Bracket

