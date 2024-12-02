
import TableEvaluator.FormulaEvaluator.parser.{Parser, Tokenizer}
import org.scalatest.FunSuite

class ParserTest extends FunSuite {

    def evaluate(expression: String): Int = {
        val tokens = new Tokenizer().tokenize(expression)
        val parser = new Parser(tokens)
        parser.parseExpr()
    }

    test("Parser Test") {
        assert(evaluate("5 - 5 + 5") == 5 )
        assert(evaluate("=5") == 5 )
        assert(evaluate("=(5 / 5) * 3 +2+1") == 6)
    }

}
