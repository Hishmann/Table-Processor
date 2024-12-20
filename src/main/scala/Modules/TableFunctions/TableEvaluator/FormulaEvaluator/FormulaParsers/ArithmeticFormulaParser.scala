package Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers

import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Brackets.{CloseBracket, OpenBracket}
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.Operators.{Add, Divide, Exponent, Multiply, Subtract, UnaryMinus}
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.Tokens.{Bracket, Operand, Operator, Token}

import scala.collection.mutable

/**
 * A class for parsing and evaluating arithmetic formulas using the Shunting-Yard algorithm and Reverse Polish Notation (RPN).
 *
 * This class tokenizes an arithmetic expression, processes it to handle operator precedence and associativity,
 * and evaluates the resulting RPN to compute the final result.
 */
class ArithmeticFormulaParser extends FormulaParser[Int] {

    // Map of supported operators with their corresponding classes
    val operators: Map[Char, Operator] = Map(
        '+' -> Add(),
        '-' -> Subtract(),
        '*' -> Multiply(),
        '/' -> Divide(),
        '^' -> Exponent()
    )

    // Map of supported brackets
    val brackets: Map[Char, Token] = Map(
        '(' -> OpenBracket('('),
        ')' -> CloseBracket(')')
    )

    /**
     * Tokenizes an arithmetic expression into a sequence of `Token` objects.
     *
     * @param expression A string representing the arithmetic expression.
     * @return A sequence of `Token` objects representing the tokenized expression.
     * @throws IllegalArgumentException if the expression contains invalid characters.
     */
    private def tokenize(expression: String): Seq[Token] = {
        val tokens = mutable.ListBuffer[Token]()
        val numberBuffer = new StringBuilder
        var equalCount = 0
        var expectUnary = true // Tracks whether the next minus sign should be treated as unary

        // Helper function to flush the number buffer and append it as an `Operand` token
        def flushNumberBuffer(): Unit = {
            if (numberBuffer.nonEmpty) {
                tokens.append(Operand(numberBuffer.toString.toInt))
                numberBuffer.clear()
                expectUnary = false
            }
        }

        // Iterate through each character in the expression
        expression.foreach {
            case c if c.isDigit =>
                numberBuffer.append(c) // Append digit to the number buffer

            case c if operators.contains(c) =>
                flushNumberBuffer()
                if (c == '-' && expectUnary) {
                    tokens.append(UnaryMinus()) // Treat minus as a unary operator
                } else {
                    tokens.append(operators(c)) // Append the operator token
                }
                expectUnary = true // After an operator, the next minus could be unary

            case c if brackets.contains(c) =>
                flushNumberBuffer()
                tokens.append(brackets(c)) // Append the bracket token
                if (c == '(') expectUnary = true // Open bracket allows unary minus
                else expectUnary = false

            case c if c.isWhitespace =>
                flushNumberBuffer() // Ignore whitespaces and flush the number buffer

            case c if c == '=' =>
                equalCount += 1
                if (equalCount > 1) throw new IllegalArgumentException("Only one Equal sign allowed")
                flushNumberBuffer() // Ignore the equals sign and flush the number buffer

            case c =>
                throw new IllegalArgumentException(s"Invalid character in expression: $c")
        }

        flushNumberBuffer() // Flush any remaining numbers in the buffer
        tokens.toSeq
    }

    /**
     * Evaluates an arithmetic expression and computes the result.
     *
     * @param expression A string representing the arithmetic expression.
     * @return The result of the evaluation as an integer.
     * @throws IllegalArgumentException if the expression is invalid or contains mismatched brackets.
     */
    def evaluate(expression: String): Int = {
        val outputQueue = mutable.Queue[Token]()
        val operatorStack = mutable.Stack[Token]()

        // Tokenize the expression
        val tokens = tokenize(expression)

        // Process tokens using the Shunting-Yard algorithm
        tokens.foreach {
            case operand: Operand =>
                outputQueue.enqueue(operand) // Add operands directly to the output queue

            case operator: Operator =>
                // Handle operators based on precedence and associativity
                while (
                    operatorStack.nonEmpty && operatorStack.top.isInstanceOf[Operator] &&
                        (operator.isRightAssociative && operator.precedence < operatorStack.top
                            .asInstanceOf[Operator]
                            .precedence || !operator.isRightAssociative && operator.precedence <= operatorStack.top
                            .asInstanceOf[Operator]
                            .precedence)
                ) {
                    outputQueue.enqueue(operatorStack.pop())
                }
                operatorStack.push(operator)

            case open: OpenBracket =>
                operatorStack.push(open) // Push open brackets onto the stack

            case close: CloseBracket =>
                // Pop operators until an open bracket is found
                while (operatorStack.nonEmpty && !operatorStack.top.isInstanceOf[OpenBracket]) {
                    outputQueue.enqueue(operatorStack.pop())
                }
                if (operatorStack.nonEmpty && operatorStack.top.isInstanceOf[OpenBracket]) {
                    operatorStack.pop() // Remove the matching open bracket
                } else {
                    throw new IllegalArgumentException("Mismatched brackets")
                }
        }

        // Pop remaining operators onto the output queue
        while (operatorStack.nonEmpty) {
            operatorStack.top match {
                case _: Bracket =>
                    throw new IllegalArgumentException("Mismatched brackets")
                case _ =>
                    outputQueue.enqueue(operatorStack.pop())
            }
        }

        val evaluationStack = mutable.Stack[Int]()

        // Evaluate the RPN expression
        outputQueue.foreach {
            case Operand(value) =>
                evaluationStack.push(value)

            case operator: Operator =>
                if (operator == UnaryMinus()) {
                    if (evaluationStack.isEmpty)
                        throw new IllegalArgumentException("Invalid expression: Unary minus has no operand")
                    val operand = evaluationStack.pop()
                    evaluationStack.push(operator.operate(operand, None))
                } else {
                    if (evaluationStack.size < 2)
                        throw new IllegalArgumentException("Invalid expression: Not enough operands for operator.")
                    val right = evaluationStack.pop()
                    val left = evaluationStack.pop()
                    evaluationStack.push(operator.operate(left, Some(right)))
                }
        }

        // Ensure only one value remains as the final result
        if (evaluationStack.size != 1)
            throw new IllegalArgumentException("Invalid expression")

        evaluationStack.pop()
    }
}
