package Modules.Commands.Variants


import org.scalatest.FunSuite
import Models.CommandModels.CommandArguments

class repeatCommandTest extends FunSuite {

    val repCommand1 = new repeatCommand(keyStr = "test", inputCount = 2)
    val test1: List[String] = List("--test", "A", "B")
    val result1: CommandArguments = CommandArguments(List("A", "B"))
    test("Basic argument parsing for repeat command") {
        assert(repCommand1.processCommand(test1) == result1)
    }

    val repCommand2 = new defaultCommand(keyStr = "test", inputCount = 2)
    val test2: List[String] = List("--test", "A", "B", "C")
    val result2: CommandArguments = CommandArguments(List())

    test("Incorrect argument count") {
        assert(repCommand2.processCommand(test2) == result2)
        assert(repCommand2.hasFailed)
    }
}

