package Table.TableFormulaEvaluator

import Table.{IntCell, EmptyCell, FormulaCell, Table}
import Table.TableFormulaEvaluator.parser.{Parser, Tokenizer}

import scala.util.matching.Regex

class TableEvaluator(val table: Table) {

    def evaluateTable(): Table = {
        val res = new Table(List())
        res.setData(table.getData.map( x => {
            x.map( y => y match {
                case formula if y.typeOfCell == (new FormulaCell("").typeOfCell) => new IntCell( cellFormulaEval(y.getCellContent.toString).toInt )
                case _ => y
            })
        }))
        res
    }

    def cellFormulaEval(formula: String): String = {
        var tempExpression: String = formula
        val cellRefRegex: Regex = """([A-Z]+)(\d+)""".r
        if (tempExpression.isEmpty) {
            throw new RuntimeException("Empty Cell referenced in a formula")
        }

        tempExpression = cellRefRegex.replaceAllIn(tempExpression, m => {
            val cellRef = m.matched // Get the matched cell reference (e.g., "A1")
            if (!NoCyclicalDep(cellRef, List[String]())) {
                throw new RuntimeException(s"Cyclical dependency found in the expression $formula")
            }
            val replacementValue = cellFormulaEval(table.tableIndexReturn(cellRef) match {
                case cell if cell.typeOfCell == (new FormulaCell("").typeOfCell) => cell.getCellContent.toString
                case cell2 if cell2.typeOfCell == (new IntCell(0).typeOfCell) => cell2.getCellContent.toString
                case _ => throw new RuntimeException("Expected a Cell[String]")
            })
            replacementValue
        })

        val tokenList = new Tokenizer().tokenize(tempExpression)
        val parser = new Parser(tokenList)
        parser.parseExpr().toString
    }

    def NoCyclicalDep(cell:String, seen: List[String]): Boolean = {
        var result : Boolean = true
        var currCellInput: String = table.tableIndexReturn(cell) match {
            case cell if cell.typeOfCell == (new FormulaCell("").typeOfCell) => cell.getCellContent.toString
            case cell2 if cell2.typeOfCell == (new IntCell(0).typeOfCell) => cell2.getCellContent.toString
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
            result = result && NoCyclicalDep(n,  List(cell) ++ seen)
        }
        result
    }
}
