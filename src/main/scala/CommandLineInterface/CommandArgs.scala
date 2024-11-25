
package CommandLineInterface

class CommandArgs(private var arguments: Map[String, List[String]] = Map()) {

        private def argsReturn(): Map[String, List[String]] = { arguments }
        private def argsUpdate(newArguments: Map[String, List[String]]): Unit = { arguments = newArguments }
        def argsEqual(arg2: CommandArgs): Boolean = { arguments == arg2.arguments }
        def argsEmpty: Boolean = { arguments.isEmpty }
        def valueFromKeyOrElse(key: String): List[String] = { arguments.getOrElse(key, List("")) }
        def addKeyAndValue(name: String, params: List[String]): Unit = {
            argsUpdate( arguments ++ Map(name -> params) )
        }

        def printCommandArgs(): Unit = println(arguments)
    }
