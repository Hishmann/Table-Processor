package Modules.Commands.Variants

import org.scalatest.FunSuite
import Models.CommandModels.CommandArguments

class defaultCommandTest extends FunSuite {

    val defCommand1 = new defaultCommand(keyStr = "test", inputCount = 1)
    val test1: List[String] = List("--test", "A")
    val result1: CommandArguments = CommandArguments(List("A"))
    test("Basic argument parsing for default command") {
        assert(defCommand1.processCommand(test1) == result1)
    }

    val defCommand2 = new defaultCommand(keyStr = "test", inputCount = 1)
    val test2: List[String] = List("--test", "A", "B")
    val result2: CommandArguments = CommandArguments(List())

    val defCommand3 = new defaultCommand(keyStr = "test", inputCount = 1)
    val test3: List[String] = List("--test")
    val result3: CommandArguments = CommandArguments(List())
    test("Incorrect argument count") {
        assert(defCommand2.processCommand(test2) == result2)
        assert(defCommand2.hasFailed)
        assert(defCommand3.processCommand(test3) == result3)
        assert(defCommand3.hasFailed)
    }
}
