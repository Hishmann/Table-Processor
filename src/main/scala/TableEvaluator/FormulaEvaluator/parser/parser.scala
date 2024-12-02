package TableEvaluator.FormulaEvaluator.parser

sealed trait Token {
    val symbol = ""
    def operation(a:Int, b: Int) : Int = { 0 }
}
case class Number(value: Int) extends Token {
}
case object Plus extends Token {
    override val symbol = "+"
    override def operation(a:Int, b: Int) : Int = { a + b }
}
case object Minus extends Token {
    override val symbol = "-"
    override def operation(a:Int, b: Int) : Int = { a - b }
}
case object Multiply extends Token {
    override val symbol = "*"
    override def operation(a:Int, b: Int) : Int = { a * b }
}
case object Divide extends Token {
    override val symbol = "/"
    override def operation(a:Int, b: Int) : Int = { a / b }
}
case object LeftParen extends Token {
    override val symbol = "("
}
case object RightParen extends Token {
    override val symbol = ")"
}

class Tokenizer {

    val tokenMap: Map[String, Token] = Map(
        Plus.symbol -> Plus,
        Minus.symbol -> Minus,
        Multiply.symbol -> Multiply,
        Divide.symbol -> Divide,
        RightParen.symbol -> RightParen,
        LeftParen.symbol -> LeftParen,
    )

    def tokenize(expr: String): List[Token] = {
        var TokenRegex = "(\\d+"
        for ((key, value) <- tokenMap) {
            TokenRegex += s"|\\$key"
        }
        TokenRegex += ")"
        val RegexPattern = s"$TokenRegex".r

        RegexPattern.findAllIn(expr).toList.map {
            case s if tokenMap.contains(s) => tokenMap(s)    // Map the operators and parentheses
            case num => Number(num.toInt)                    // Convert matched numbers to Number tokens
        }
    }
}


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
