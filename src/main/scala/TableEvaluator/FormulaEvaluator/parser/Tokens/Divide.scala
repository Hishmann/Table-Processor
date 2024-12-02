package TableEvaluator.FormulaEvaluator.parser.Tokens

case object Divide extends Token {
    override val symbol = "/"
    override def operation(a:Int, b: Int) : Int = { a / b }
}
