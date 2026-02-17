package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaFormats

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.ArithmeticFormulaParser

/**
 * A class for validating the format of arithmetic table formulas.
 *
 * The `ArithmeticTableFormulaFormat` class checks if a given formula adheres to the syntax
 * and structural rules required for arithmetic expressions, particularly in the context of tables.
 * It uses the `ArithmeticFormulaParser` to identify valid operators and brackets for token validation.
 */
class ArithmeticTableFormulaFormat extends FormulaFormat {

    // Instance of the ArithmeticFormulaParser to access operators and brackets
    val parser = new ArithmeticFormulaParser

    /**
     * Validates the format of a given arithmetic formula.
     *
     * @param expression A string representing the formula to be validated.
     * @return `true` if the formula is valid, `false` otherwise.
     */
    def validFormat(expressionInput: String): Boolean = {
        // Formula must start with an '=' character
        val expression = expressionInput.trim
        if (expression.startsWith("=")) {
            // Build a regex pattern to allow valid tokens (operators, brackets, letters, numbers, and spaces)
            var TokenRegex = "^[A-Z0-9\\="
            for ((key, _) <- parser.operators) {
                TokenRegex += s"\\$key" // Escape operator characters
            }
            for ((key, _) <- parser.brackets) {
                TokenRegex += s"\\$key" // Escape bracket characters
            }
            TokenRegex += " ]*$" // Close the pattern to allow valid trailing characters

            val validPattern = s"$TokenRegex".r // Compile the pattern into a regex

            // Return false if the expression contains invalid characters
            if (!validPattern.matches(expression)) {
                return false
            }

            // Patterns for invalid consecutive tokens or structural errors
            val invalidConsecutivePattern = """(\d+[A-Z]+|[A-Z]+\d+[\s]+[A-Z]+\d+)""".r
            val invalidPattern1 = """[A-Z]+\d+[A-Z]+""".r   // Invalid: digits followed by letters without a valid separator (e.g., A1B)
            val invalidPattern2 = """\d+[A-Z]+""".r         // Invalid: letters following digits directly (e.g., 1A)

            // Check for structural or consecutive invalid patterns
            if (invalidPattern1.findFirstIn(expression).isDefined ||
                invalidPattern2.findFirstIn(expression).isDefined ||
                invalidConsecutivePattern.findFirstIn(expression).isDefined) {
                return false // Invalid pattern detected
            }

            // Final match validation to ensure compliance with the token structure
            expression match {
                case validPattern() => true  // The formula matches the valid token pattern
                case _ => false              // The formula contains invalid characters
            }
        } else {
            false // The formula does not start with '='
        }
    }
}

