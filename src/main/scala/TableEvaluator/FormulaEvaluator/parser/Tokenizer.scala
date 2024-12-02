package TableEvaluator.FormulaEvaluator.parser

import TableEvaluator.FormulaEvaluator.parser.Tokens._

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

