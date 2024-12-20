package TableProcessor.CommandLineInterface

import Models.CommandModels.{CommandArguments, CommandArgumentsCollection}
import TableProcessor.CommandUtils.CommandUtils
import Modules.Commands.Command
import org.scalatest.FunSuite

class CommandLineInterfaceTest extends FunSuite {

    test("Basic test") {
        val CLI = new CommandLineInterface(List(
            "--"+CommandUtils.inputFileCommandName,"src/main/Files/test.csv",
            "--"+CommandUtils.filterEmptyCommandName,"B",
            "--"+CommandUtils.rangeCommandName,"A1","B2"
        ), CommandUtils.commandList)
        val CA: CommandArgumentsCollection = CLI.runCommands()
        val test: CommandArgumentsCollection = CommandArgumentsCollection()
        test.addArgAndParam(CommandUtils.inputFileCommandName, CommandArguments(List("src/main/Files/test.csv")))
        test.addArgAndParam(CommandUtils.filterEmptyCommandName, CommandArguments(List("B")))
        test.addArgAndParam(CommandUtils.rangeCommandName, CommandArguments(List("A1","B2")))
        assert(CA.checkEquality(test))
    }

    val commandLineArgsTest1: List[String] = List(
        "--"+CommandUtils.inputFileCommandName , "src/test/FilesTest/test1.csv",
        "--"+CommandUtils.outputSeparatorCommandName, ",",
        "--"+CommandUtils.inputSeparatorCommandName , ",",
        "--"+CommandUtils.outputformatCommandName , "csv",
        "--"+CommandUtils.rangeCommandName , "A1", "B2",
        "--"+CommandUtils.filterEmptyCommandName , "A",
        "--"+CommandUtils.filterEmptyCommandName , "B"
    )
    val commandListCopy1: List[Command] = CommandUtils.commandList.map(x => x.deepCopy)
    test ("Basic command acceptor") {
        val CLI = new CommandLineInterface(commandLineArgsTest1, commandListCopy1)
        val CA: CommandArgumentsCollection = CLI.runCommands()
        val test: CommandArgumentsCollection = CommandArgumentsCollection()
        test.addArgAndParam(CommandUtils.inputFileCommandName, CommandArguments(List("src/test/FilesTest/test1.csv")))
        test.addArgAndParam(CommandUtils.outputSeparatorCommandName, CommandArguments(List(",")))
        test.addArgAndParam(CommandUtils.inputSeparatorCommandName, CommandArguments(List(",")))
        test.addArgAndParam(CommandUtils.outputformatCommandName, CommandArguments(List("csv")))
        test.addArgAndParam(CommandUtils.rangeCommandName, CommandArguments(List("A1","B2")))
        test.addArgAndParam(CommandUtils.filterEmptyCommandName, CommandArguments(List("A","B")))
        assert(CA.checkEquality(test))
    }

    val commandLineArgsTest2: List[String] = List(
        "--blah", "--bad", "--input-file", "src/test/FilesTest/test1.csv","--format", "csv"
    )
    val commandListCopy2: List[Command] = CommandUtils.commandList.map(x => x.deepCopy)

    test("Invalid commands") {
        val CLI = new CommandLineInterface(commandLineArgsTest2, commandListCopy2)
        assertThrows[RuntimeException] {
            CLI.runCommands()
        }
    }

    val commandLineArgsTest3: List[String] = List(
        "--"+CommandUtils.inputFileCommandName , "src/test/FilesTest/test1.csv",
        "--"+CommandUtils.rangeCommandName , "A1"
    )
    val commandListCopy3: List[Command] = CommandUtils.commandList.map(x => x.deepCopy)

    test("Insufficient command inputs") {
        val CLI = new CommandLineInterface(commandLineArgsTest3, commandListCopy3)
        assertThrows[RuntimeException] {
            CLI.runCommands()
        }
    }

//    val commandLineArgsTest4: List[String] = List(
//        "--"+CommandUtils.inputFileCommandName , "src/test/FilesTest/test1.csv",
//        "--"+CommandUtils.outputSeparatorCommandName , ",",
//        "--"+CommandUtils.filterEmptyCommandName , "A",
//        "--"+CommandUtils.separatorCommandName , ",",
//        "--"+CommandUtils.outputformatCommandName , "csv",
//        "--"+CommandUtils.filterEmptyCommandName , "B",
//        "--"+CommandUtils.rangeCommandName , "A1", "B2",
//        "--"+CommandUtils.filterEmptyCommandName , "C"
//    )
//    val commandListCopy4: List[command] = CommandUtils.commandList.map(x => x.deepCopy)
//
//    test ("Repeat commands like filters") {
//        val CLI = new CLI(commandLineArgsTest4, commandListCopy4)
//
//        assert(
//            CommandArguments( Map(
//                CommandUtils.inputFileCommandName -> List("src/test/FilesTest/test1.csv"),
//                CommandUtils.outputSeparatorCommandName -> List(","),
//                CommandUtils.separatorCommandName -> List(","),
//                CommandUtils.outputformatCommandName -> List("csv"),
//                CommandUtils.rangeCommandName -> List("A1", "B2"),
//                CommandUtils.filterEmptyCommandName -> List("A", "B", "C")
//            )) == (CLI.runCommands())
//        )
//    }

}
