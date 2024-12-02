
package Models

case class CommandArguments(private var arguments: Map[String, List[String]] = Map()) {
    def argsEmpty: Boolean = { arguments.isEmpty }
    def valueFromArgOrEmpty(key: String): Option[List[String]] = { arguments.get(key) }
    def addArgAndParam(name: String, params: List[String]): Unit = {arguments = arguments ++ Map(name -> params)}
}
