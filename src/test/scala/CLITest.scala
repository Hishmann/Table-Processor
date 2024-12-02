import org.scalatest.FunSuite
import CommandLineInterface.CLI
import Models.CommandArguments
import CommandUtils.CommandUtils
import Commands.command

class CLITest extends FunSuite {

    val commandLineArgsTest1: List[String] = List(
        "--"+CommandUtils.inputFileCommandName , "src/test/FilesTest/test1.csv",
        "--"+CommandUtils.outputSeparatorCommandName, ",",
        "--"+CommandUtils.separatorCommandName , ",",
        "--"+CommandUtils.formatCommandName , "csv",
        "--"+CommandUtils.rangeCommandName , "A1", "B2"
    )
    val commandListCopy1: List[command] = CommandUtils.commandList.map(x => x.deepCopy)

    test ("Basic command acceptor") {
        val CLI = new CLI(commandLineArgsTest1, commandListCopy1)
        assert(
            CommandArguments( Map(CommandUtils.inputFileCommandName -> List("src/test/FilesTest/test1.csv"),
                CommandUtils.outputSeparatorCommandName -> List(","),
                CommandUtils.separatorCommandName -> List(","),
                CommandUtils.formatCommandName -> List("csv"),
                CommandUtils.rangeCommandName -> List("A1", "B2")
            )) == (CLI.runCommands()))
    }

    val commandLineArgsTest2: List[String] = List(
        "--blah", "--bad", "--input-file", "src/test/FilesTest/test1.csv","--format", "csv"
    )
    val commandListCopy2: List[command] = CommandUtils.commandList.map(x => x.deepCopy)

    test("Invalid commands") {
        val CLI = new CLI(commandLineArgsTest2, commandListCopy2)
        assertThrows[RuntimeException] {
            CLI.runCommands()
        }
    }

    val commandLineArgsTest3: List[String] = List(
        "--"+CommandUtils.inputFileCommandName , "src/test/FilesTest/test1.csv",
        "--"+CommandUtils.rangeCommandName , "A1"
    )
    val commandListCopy3: List[command] = CommandUtils.commandList.map(x => x.deepCopy)

    test("Insufficient command inputs") {
        val CLI = new CLI(commandLineArgsTest3, commandListCopy3)
        assertThrows[RuntimeException] {
            CLI.runCommands()
        }
    }

    val commandLineArgsTest4: List[String] = List(
        "--"+CommandUtils.inputFileCommandName , "src/test/FilesTest/test1.csv",
        "--"+CommandUtils.outputSeparatorCommandName , ",",
        "--"+CommandUtils.filterEmptyCommandName , "A",
        "--"+CommandUtils.separatorCommandName , ",",
        "--"+CommandUtils.formatCommandName , "csv",
        "--"+CommandUtils.filterEmptyCommandName , "B",
        "--"+CommandUtils.rangeCommandName , "A1", "B2",
        "--"+CommandUtils.filterEmptyCommandName , "C"
    )
    val commandListCopy4: List[command] = CommandUtils.commandList.map(x => x.deepCopy)

    test ("Repeat commands like filters") {
        val CLI = new CLI(commandLineArgsTest4, commandListCopy4)

        assert(
            CommandArguments( Map(
                CommandUtils.inputFileCommandName -> List("src/test/FilesTest/test1.csv"),
                CommandUtils.outputSeparatorCommandName -> List(","),
                CommandUtils.separatorCommandName -> List(","),
                CommandUtils.formatCommandName -> List("csv"),
                CommandUtils.rangeCommandName -> List("A1", "B2"),
                CommandUtils.filterEmptyCommandName -> List("A", "B", "C")
            )) == (CLI.runCommands())
        )
    }

}
