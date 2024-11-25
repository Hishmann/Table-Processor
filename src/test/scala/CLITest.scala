import org.scalatest.FunSuite
import CommandLineInterface.{CLI, CommandArgs}
import commands._
class CLITest extends FunSuite {

    val commandList: List[command] = List(
        new inputFileCommand(),
        new helpCommand(),
        new formatCommand(),
        new separatorCommand(),
        new outputSeparatorCommand(),
        new rangeCommand(),
        new filterIsEmptyCommand()
    )
    val commandArgsDef1 = new CommandArgs(Map())
    val commandLineArgsTest1: List[String] = List(
        (new inputFileCommand()).keyStrReturn , "src/test/FilesTest/test1.csv",
        (new outputSeparatorCommand()).keyStrReturn , ",",
        (new separatorCommand()).keyStrReturn , ",",
        (new formatCommand()).keyStrReturn , "csv",
        (new rangeCommand()).keyStrReturn , "A1", "B2"
    )

    test ("Basic command acceptor") {
        val CLI = new CLI(commandLineArgsTest1, commandArgsDef1, commandList)
        CLI.runCommands()
        assert(
            new CommandArgs( Map((new inputFileCommand()).keyStrReturn -> List("src/test/FilesTest/test1.csv"),
                (new outputSeparatorCommand()).keyStrReturn -> List(","),
                (new separatorCommand()).keyStrReturn -> List(","),
                (new formatCommand()).keyStrReturn -> List("csv"),
                (new rangeCommand).keyStrReturn -> List("A1", "B2")) ).argsEqual(CLI.setupReturn())
            )
    }

    val commandArgsDef2 = new CommandArgs(Map())
    val commandLineArgsTest2: List[String] = List(
        "--blah", "--bad", "--input-file", "src/test/FilesTest/test1.csv","--format", "csv"
    )

    test("Invalid commands") {
        val CLI = new CLI(commandLineArgsTest2 , commandArgsDef2, commandList)
        assertThrows[RuntimeException] {
            CLI.runCommands()
        }
    }

    val commandArgsDef3 = new CommandArgs(Map())
    val commandLineArgsTest3: List[String] = List(
        (new rangeCommand).keyStrReturn , "A1"
    )

    test("Insufficient command inputs") {
        val CLI = new CLI(commandLineArgsTest3 , commandArgsDef3, commandList)
        assertThrows[RuntimeException] {
            CLI.runCommands()
        }
    }

    val commandArgsDef4 = new CommandArgs(Map())
    val commandLineArgsTest4: List[String] = List(
        (new inputFileCommand()).keyStrReturn , "src/test/FilesTest/test1.csv",
        (new outputSeparatorCommand()).keyStrReturn , ",",
        (new filterIsEmptyCommand()).keyStrReturn , "A",
        (new separatorCommand()).keyStrReturn , ",",
        (new formatCommand()).keyStrReturn , "csv",
        (new filterIsEmptyCommand()).keyStrReturn , "B",
        (new rangeCommand()).keyStrReturn , "A1", "B2",
        (new filterIsEmptyCommand()).keyStrReturn , "C"
    )

    val commandList4: List[command] = List(
        new inputFileCommand(),
        new helpCommand(),
        new formatCommand(),
        new separatorCommand(),
        new outputSeparatorCommand(),
        new rangeCommand(),
        new filterIsEmptyCommand()
    )

    test ("Repeat commands like filters") {
        val CLI = new CLI(commandLineArgsTest4, commandArgsDef4, commandList4)
        CLI.runCommands()

        assert(
            new CommandArgs( Map(
                (new inputFileCommand()).keyStrReturn -> List("src/test/FilesTest/test1.csv"),
                (new outputSeparatorCommand()).keyStrReturn -> List(","),
                (new separatorCommand()).keyStrReturn -> List(","),
                (new formatCommand()).keyStrReturn -> List("csv"),
                (new rangeCommand).keyStrReturn -> List("A1", "B2"),
                (new filterIsEmptyCommand()).keyStrReturn -> List("A", "B", "C")
            )).argsEqual(CLI.setupReturn())
        )
    }

}
