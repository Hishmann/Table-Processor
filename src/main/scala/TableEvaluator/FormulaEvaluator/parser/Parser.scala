package TableEvaluator.FormulaEvaluator.parser

import Tokens._

class Parser(tokens: List[Token]) {
    private var position: Int = 0

    // Helper to get the current token
    def currentToken: Token = if (position < tokens.length) tokens(position) else null

    // Helper to move to the next token
    def nextToken(): Unit = {
        position += 1
    }

    // Parse an expression (Expr := Term (("+" | "-") Term)*)
    def parseExpr(): Int = {
        var result = parseTerm()
        while (currentToken == Plus || currentToken == Minus) {
            val rememberedToken : Token = currentToken
            currentToken match {
                case Plus =>
                    nextToken()
                    result = rememberedToken.operation(result, parseTerm())
                case Minus =>
                    nextToken()
                    result = rememberedToken.operation(result, parseTerm())
                case _ => // Do nothing
            }
        }
        result
    }

    // Parse a term (Term := Factor (("*" | "/") Factor)*)
    def parseTerm(): Int = {
        var result = parseFactor()
        while (currentToken == Multiply || currentToken == Divide) {
            val rememberedToken : Token = currentToken
            currentToken match {
                case Divide =>
                    nextToken()
                    result = rememberedToken.operation(result, parseTerm())
                case Multiply =>
                    nextToken()
                    result = rememberedToken.operation(result, parseTerm())
                case _ => // Do nothing
            }
        }
        result
    }

    // Parse a factor (Factor := ("-" Factor) | Number | "(" Expr ")")
    def parseFactor(): Int = {
        currentToken match {
            case Minus =>  // Handle unary minus
                nextToken()
                -parseFactor()
            case Number(value) =>
                nextToken()
                value
            case LeftParen =>
                nextToken()
                val result = parseExpr()
                if (currentToken != RightParen) {
                    throw new RuntimeException("Missing closing parenthesis")
                }
                nextToken()
                result
            case _ => throw new RuntimeException("Unexpected token in the formula")
        }
    }
}
