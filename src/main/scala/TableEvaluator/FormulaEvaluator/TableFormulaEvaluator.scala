package TableEvaluator.FormulaEvaluator

import Models.Table.Cells.{FormulaCell, IntCell}
import Models.Table.Table
import TableEvaluator.TableEvaluator
import TableFormulaEvaluatorUtil.cellFormulaEval

class TableFormulaEvaluator extends TableEvaluator {
    def evaluate(input: Table): Table = {
        val res = Table()
        res.setData(input.getData.map( x => {
            x.map {
                case FormulaCell(cellContent) => new IntCell(cellFormulaEval(cellContent.toString, input).toInt)
                case y => y
            }
        }))
        res
    }
}
