package Models.Table.Cells

case class IntCell(override val cellContent: Int) extends Cell[Int](cellContent, typeOfCell = "Int") {
}
