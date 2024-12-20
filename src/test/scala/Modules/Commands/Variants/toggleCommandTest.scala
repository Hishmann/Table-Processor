package Modules.Commands.Variants

import Models.CommandModels.CommandArguments
import org.scalatest.FunSuite

class toggleCommandTest extends FunSuite {

    val togCommand1 = new toggleCommand(keyStr = "test", inputCount = 0)
    val test1: List[String] = List("--test")
    val result1: CommandArguments = CommandArguments(List(""))
    test("Basic argument parsing for repeat command") {
        assert(togCommand1.processCommand(test1) == result1)
    }

    val togCommand2 = new toggleCommand(keyStr = "test", inputCount = 0)
    val test2: List[String] = List("--test", "A")
    val result2: CommandArguments = CommandArguments(List())

    test("Incorrect argument count") {
        assert(togCommand2.processCommand(test2) == result2)
        assert(togCommand2.hasFailed)
    }
}
