package CommandLineInterface
import commands.{command, inputFileCommand, helpCommand}

class CLI(val arguments: List[String], private val setup: CommandArgs, private val allCommands: List[command]) {

    def setupReturn(): CommandArgs = setup
    private def runHelp(): Unit = { for (command <- allCommands) { command.printHelp() } }
    private def invalidInput(): Unit = {
        throw new RuntimeException("No input file mentioned or Invalid command line arguments\n" +
            "Please try --help or -h for guidance.")
    }

    def runCommands(): Unit = {
        for (command <- allCommands) {
            for (arg <- arguments) {
                if ((arg.startsWith("--") && !allCommands.exists(_.keyStrReturn == arg))
                && (arg.startsWith("-") && !allCommands.exists(_.keyStr2Return == arg))) {
                    println(s"Invalid command found: $arg")
                    invalidInput()
                }
            }

            val params : List[String] = command.processCommand(arguments)

            if (params.nonEmpty) {
                setup.addKeyAndValue(command.keyStrReturn, params)
            }

            if (command.hasFailed) {
                invalidInput()
            }
        }
        val helpCommand: command = new helpCommand()
        if (setup.valueFromKeyOrElse(helpCommand.keyStrReturn) == List(helpCommand.toggleValueReturn)) {
            runHelp()
            return
        }
        if (setup.valueFromKeyOrElse(new inputFileCommand().keyStrReturn) == List("")) {
            invalidInput()
        }
        if (setup.argsEmpty) {
            invalidInput()
        }
    }

}