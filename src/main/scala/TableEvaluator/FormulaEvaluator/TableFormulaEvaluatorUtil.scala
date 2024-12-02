package TableEvaluator.FormulaEvaluator

import Models.Table.Cells.{Cell, FormulaCell, IntCell}
import Models.Table.Table
import TableEvaluator.FormulaEvaluator.parser.{Parser, Tokenizer}

import scala.util.matching.Regex

object TableFormulaEvaluatorUtil {

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

    def tableIndexReturn(ind: String, table: Table): Cell[_] = {
        val cellRefRegex = """([A-Z]+)(\d+)""".r  // Regular expression to match column (letters) and row (digits)
        val (column, row) = ind match {
            case cellRefRegex(letters, digits) => (letters, digits)
            case _ => throw new RuntimeException(s"Invalid cell reference: $ind")
        }
        val col: Int = column.foldLeft(0) { (result, char) =>
            result * 26 + (char - 'A')
        }
        table.getData(row.toInt - 1)(col)
    }


    def NoCyclicalDep(cell:String, seen: List[String], table: Table): Boolean = {
        var result : Boolean = true
        var currCellInput: String = tableIndexReturn(cell, table) match {
            case IntCell(cellContent) => cellContent.toString
            case FormulaCell(cellContent) => cellContent
            case _ => throw new RuntimeException("Expected a Cell[String]")
        }
        if (seen.contains(cell)) {
            return false;
        }
        val cellRefRegex: Regex = """([A-Z]+)(\d+)""".r
        val newIndices = cellRefRegex.findAllIn(currCellInput).toList
        if (newIndices.isEmpty) {
            return true;
        }
        for (n <- newIndices) {
            result = result && NoCyclicalDep(n,  List(cell) ++ seen, table)
        }
        result
    }

    def cellFormulaEval(formula: String,  table: Table): String = {
        var tempExpression: String = formula
        val cellRefRegex: Regex = """([A-Z]+)(\d+)""".r
        if (tempExpression.isEmpty) {
            throw new RuntimeException("Empty Cell referenced in a formula")
        }

        tempExpression = cellRefRegex.replaceAllIn(tempExpression, m => {
            val cellRef = m.matched // Get the matched cell reference (e.g., "A1")
            if (!NoCyclicalDep(cellRef, List[String](), table)) {
                throw new RuntimeException(s"Cyclical dependency found in the expression $formula")
            }
            val replacementValue = cellFormulaEval( tableIndexReturn(cellRef, table) match {
                case IntCell(cellContent) => cellContent.toString
                case FormulaCell(cellContent) => cellContent
                case _ => throw new RuntimeException("Expected a Cell[String]")
            }, table)
            replacementValue
        })

        val tokenList = new Tokenizer().tokenize(tempExpression)
        val parser = new Parser(tokenList)
        parser.parseExpr().toString
    }
}
