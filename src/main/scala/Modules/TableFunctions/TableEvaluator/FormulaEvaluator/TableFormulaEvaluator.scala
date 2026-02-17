package Modules.TableFunctions.TableEvaluator.FormulaEvaluator

import scala.util.matching.Regex
import Models.Table.Cells.{Cell, FormulaCell, IntCell}
import Models.Table.Table
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaFormats.FormulaFormat
import Modules.TableFunctions.TableEvaluator.FormulaEvaluator.FormulaParsers.FormulaParser
import Modules.TableFunctions.TableEvaluator.TableEvaluator
import Modules.TableFunctions.ReferencingIndices.tableIndexReturn

class TableFormulaEvaluator(val formulaFormat: FormulaFormat,
                           val parser: FormulaParser[_]) extends TableEvaluator {

    def evaluate(input: Table): Table = {
        val res = Table()
        res.setData(input.getData.map( x => {
            x.map {
                case FormulaCell(cellContent) =>
                    if (!formulaFormat.validFormat(cellContent)) {
                        throw new RuntimeException(s"Invalid Expression: $cellContent")
                    }
                    IntCell(cellFormulaEval(cellContent, input).toInt)
                case y => y
            }
        }))
        res
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
                case FormulaCell(cellContent) =>
                    if (!formulaFormat.validFormat(cellContent)) {
                        throw new RuntimeException(s"Invalid Expression: $cellContent")
                    }
                    cellContent
                case _ => throw new RuntimeException("Expected a Cell[String]")
            }, table)
            replacementValue
        })

        parser.evaluate(tempExpression).toString
    }

    def NoCyclicalDep(cell:String, seen: List[String], table: Table): Boolean = {
        var result : Boolean = true
        var currCellInput: String = tableIndexReturn(cell, table) match {
            case IntCell(cellContent) => cellContent.toString
            case FormulaCell(cellContent) =>
                if (!formulaFormat.validFormat(cellContent)) {
                    throw new RuntimeException(s"Invalid Expression: $cellContent")
                }
                cellContent
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
}

