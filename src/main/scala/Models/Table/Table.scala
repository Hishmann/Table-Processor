package Models.Table

import Models.Table.Cells.Cell

case class Table() {
    private var data: List[List[Cell[_]]] = List()
    private var rows: List[Int] = List()
    private var columns: List[Int] = List()

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
}
