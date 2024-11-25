package Table.TableFormulaEvaluator.parser

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


object FormulaChecker {
    def cellFormulaValidity(cell: String) : Boolean = {
        if (cell.startsWith("=")) {
            var TokenRegex = "^[A-Z0-9\\="
            for ((key, value) <- new Tokenizer().tokenMap) {
                TokenRegex += s"\\$key"
            }
            TokenRegex += " ]*$"

            val validPattern = s"$TokenRegex".r
            if (!validPattern.matches(cell)) { return false }

            val invalidConsecutivePattern = """(\d+[A-Z]+|[A-Z]+\d+[\s]+[A-Z]+\d+)""".r
            val invalidPattern1 = """[A-Z]+\d+[A-Z]+""".r   // Invalid if digits are followed by letters (e.g., A1B)
            val invalidPattern2 = """\d+[A-Z]+""".r  // Invalid if letters come after digits in a cell reference

            // Check for invalid patterns (invalid cell references or numbers without operators)
            if (invalidPattern1.findFirstIn(cell).isDefined ||
                invalidPattern2.findFirstIn(cell).isDefined ||
                invalidConsecutivePattern.findFirstIn(cell).isDefined) {
                return false // Invalid pattern found, flag it
            }

            cell match {
                case validPattern() => true  // The string is valid, no need to flag it
                case _ => false  // The string contains invalid characters, flag it
            }
        } else {
            false
        }
    }
}