package Models.Parameters

case class LoadDataTableParameters(val inputFile: String = "",
                                   val separator: String = "",
                                   val format: String = "") {}
