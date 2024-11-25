package Table
import TableFormulaEvaluator.parser.FormulaChecker.cellFormulaValidity

class Cell[T](private var cellContent: T, val typeOfCell: String) {
    def getCellContent: T = cellContent
    def setCellContent(newVal: T): Unit = { cellContent = newVal }
}

class IntCell(cellContent: Int) extends Cell[Int](cellContent, typeOfCell = "Int") {
}

class FormulaCell(cellContent: String) extends Cell[String](cellContent, typeOfCell = "Formula") {
}

class EmptyCell extends Cell[String](cellContent = "", typeOfCell = "Empty") {
}

class Table(inputData: List[List[String]]) {
    private var data: List[List[Cell[_]]] = List()
    private var rows: List[Int] = List()
    private var columns: List[Int] = List()

    dataInitializer()

    def getRows: List[Int] = rows
    def setRows(n: List[Int]) = {rows = n}
    def getColumns: List[Int] = columns
    def setColumns(n: List[Int]) = {columns = n}
    def getData: List[List[Cell[_]]] = data
    def setData(newData: List[List[Cell[_]]]): Unit = {
        data = newData
        rows = (0 to data.length-1).toList
        columns = (0 to data.head.length-1).toList
    }

    def isEqual(otherTable: Table): Boolean = {
        if (data.length != otherTable.getData.length ||
            data.head.length != otherTable.getData.head.length) {
            return false
        }
        for (i <- 0 to data.length-1) {
            for (j <- 0 to data.head.length-1) {
                if (data(i)(j).getCellContent != otherTable.getData(i)(j).getCellContent ||
                    data(i)(j).typeOfCell != otherTable.getData(i)(j).typeOfCell) {
                    return false
                }
            }
        }
        true
    }

    def columnToIndex(column: String): Int = {
        column.toUpperCase.foldLeft(0) { (acc, char) =>
            acc * 26 + (char - 'A')
        }
    }

    def tableIndexReturn(ind: String): Cell[_] = {
        val cellRefRegex = """([A-Z]+)(\d+)""".r  // Regular expression to match column (letters) and row (digits)
        val (column, row) = ind match {
            case cellRefRegex(letters, digits) => (letters, digits)
            case _ => throw new RuntimeException(s"Invalid cell reference: $ind")
        }
        val col: Int = column.foldLeft(0) { (result, char) =>
            result * 26 + (char - 'A')
        }
        data(row.toInt - 1)(col)
    }

    def printTable(): Unit = {
        for (i <- rows) {
            var temp: String = ""
            for (j <- columns) {
                temp += data(i)(j).getCellContent + " "
            }
            println(temp)
        }
    }

    def dataInitializer(): Unit = {
        var formula: String = ""
        var result : List[List[Cell[_]]] = List()

        if (inputData.isEmpty) { return }
        for (i <- 0 to inputData.length - 2) {
            if (inputData(i).length != inputData(i+1).length) {
                throw new RuntimeException("The CSV format is wrong")
            }
        }

        rows = inputData.indices.toList
        columns = inputData.head.indices.toList

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
        data = result
    }
}



