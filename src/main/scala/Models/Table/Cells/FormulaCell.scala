package Models.Table.Cells

case class FormulaCell(override val cellContent: String) extends Cell[String](cellContent, typeOfCell = "Formula") {
}
