package TableCreator
import TableCreator.TableCreator
import Models.Table.Table
import Models.Table.Cells.{Cell, EmptyCell, FormulaCell, IntCell}
import TableEvaluator.FormulaEvaluator.TableFormulaEvaluatorUtil.cellFormulaValidity

class TableCreatorFromListStrings extends TableCreator[List[List[String]]] {

    def dataInitializer(inputData: List[List[String]]): Table = {
        var formula: String = ""
        var result : List[List[Cell[_]]] = List()
        var resultTable : Table = Table()

        if (inputData.isEmpty) { return resultTable }
        for (i <- 0 to inputData.length - 2) {
            if (inputData(i).length != inputData(i+1).length) {
                throw new RuntimeException("The CSV format is wrong")
            }
        }

        resultTable.setRows(inputData.indices.toList)
        resultTable.setColumns(inputData.head.indices.toList)

        for (i <- inputData.indices.toList) {
            var innerRow : List[Cell[_]] = List()
            for (j <- inputData(i).indices.toList) {
                formula = inputData(i)(j)
                var temp: Cell[_] = new EmptyCell()

                if (!formula.forall(_.isDigit) && formula.nonEmpty) {
                    if (cellFormulaValidity(formula)) {
                        temp = new FormulaCell(formula)
                    } else {
                        throw new RuntimeException(s"Invalid formula: $formula")
                    }
                } else if (formula.nonEmpty) {
                    temp = new IntCell(formula.toInt)
                }
                innerRow = innerRow :+ temp
            }
            result = result :+ innerRow
        }
        resultTable.setData(result)
        resultTable
    }
}



