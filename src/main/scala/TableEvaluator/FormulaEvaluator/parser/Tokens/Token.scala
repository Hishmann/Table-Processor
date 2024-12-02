package TableEvaluator.FormulaEvaluator.parser.Tokens

trait Token {
    val symbol = ""
    def operation(a:Int, b: Int) : Int = { 0 }
}
