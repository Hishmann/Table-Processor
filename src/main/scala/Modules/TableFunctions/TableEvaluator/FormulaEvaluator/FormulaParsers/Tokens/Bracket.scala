package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens

/**
 * A trait representing a bracket token, extending the `Token` trait.
 *
 * The `Bracket` trait defines the basic properties of a bracket, such as its type.
 * Brackets are commonly used in expression parsing to represent grouping or precedence.
 */
trait Bracket extends Token {
    /**
     * The type of the bracket.
     *
     * @return A character representing the type of the bracket.
     *         For example:
     *         - '(' for opening parentheses.
     *         - ')' for closing parentheses.
     *         - '{' for opening curly braces.
     *         - '}' for closing curly braces.
     */
    def bracketType: Char
}
