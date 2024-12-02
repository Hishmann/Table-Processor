package CommandLineInterface

import Models.CommandArguments
import Commands.command
import CommandUtils.CommandUtils

class CLI(val arguments: List[String], val allCommands: List[command]) {

    private def runHelp(): Unit = { for (command <- allCommands) { println(command.helpStr) } }
    private def invalidInput(s: String = "", nonExistingCommand: Boolean = false): Unit = {
        if (nonExistingCommand) {
            throw new RuntimeException(s"Invalid command found: $s \n" +
                "Please try --help or -h for guidance.")
        }
        if (s.isEmpty) {
            throw new RuntimeException("No input file mentioned or Invalid command line arguments\n" +
                "Please try --help or -h for guidance.")
        } else {
            throw new RuntimeException(s"Invalid command line arguments for $s \n" +
                "Please try --help or -h for guidance.")
        }
    }

    def runCommands(): CommandArguments = {
        var setup: CommandArguments = CommandArguments()
        for (arg <- arguments) {
            if ((arg.startsWith("--") && !allCommands.exists("--" + _.keyStr == arg))
                && (arg.startsWith("-") && !allCommands.exists("-" + _.keyStr2 == arg))) {
                invalidInput(arg, nonExistingCommand = true)
            }
        }
        var inputFileIncluded: Boolean = false
        for (command <- allCommands) {
            val params: List[String] = command.processCommand(arguments)
            if (params.nonEmpty && command.hasSeen) {
                setup.addArgAndParam(command.keyStr, params)
            }
            if (command.hasFailed) {
                invalidInput(command.keyStr)
            }
            if (command.keyStr == CommandUtils.helpCommandName && command.hasSeen) {
                runHelp()
                return new CommandArguments()
            }
            if (command.keyStr == CommandUtils.inputFileCommandName && command.hasSeen && !command.hasFailed) {
                inputFileIncluded = true
            }
        }
        if (!inputFileIncluded || setup.argsEmpty) {
            invalidInput()
        }
        setup
    }
}