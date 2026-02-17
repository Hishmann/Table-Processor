package TableProcessor.CommandLineInterface

import Models.CommandModels.{CommandArguments, CommandArgumentsCollection}
import Modules.Commands.Command
import TableProcessor.CommandUtils.CommandUtils

/**
 * CommandLineInterface handles parsing and validating command-line arguments.
 * @param arguments List of command-line arguments passed to the program.
 * @param allCommands List of all possible commands the interface can process.
 */
class CommandLineInterface(val arguments: List[String], val allCommands: List[Command]) {

    /**
     * Displays the help message for all available commands.
     */
    private def runHelp(): Unit = {
        for (command <- allCommands) {
            println(command.helpStr) // Print the help string for each command
        }
    }

    /**
     * Handles invalid input scenarios and throws runtime exceptions with appropriate messages.
     * @param s Optional string containing the invalid argument (default: empty).
     * @param nonExistingCommand Flag indicating if the invalid input is a non-existing command.
     */
    private def invalidInput(s: String = "", nonExistingCommand: Boolean = false): Unit = {
        if (nonExistingCommand) {
            throw new RuntimeException(s"Invalid command found: $s \n" +
                "Please try --help or -h for guidance.")
        }
        if (s.isEmpty) {
            throw new RuntimeException("No input file location specified\n" +
                "Please try --help or -h for guidance.")
        } else {
            throw new RuntimeException(s"Invalid command line arguments for $s \n" +
                "Please try --help or -h for guidance.")
        }
    }

    /**
     * Parses and validates command-line arguments, returning a collection of command arguments.
     * @return CommandArgumentsCollection containing parsed arguments and parameters.
     */
    def runCommands(): CommandArgumentsCollection = {
        // Initialize an empty collection to store command arguments and parameters
        var setup: CommandArgumentsCollection = CommandArgumentsCollection()

        // Validate that each argument corresponds to a valid command
        for (arg <- arguments) {
            if ((arg.startsWith("--") && !allCommands.exists("--" + _.keyStr == arg))
                && (arg.startsWith("-") && !allCommands.exists("-" + _.keyStr2 == arg))) {
                invalidInput(arg, nonExistingCommand = true) // Handle invalid commands
            }
        }

        // Track whether an input file has been included in the arguments
        var inputFileIncluded: Boolean = false

        // Process each command in the list of all commands
        for (command <- allCommands) {
            val params: CommandArguments = command.processCommand(arguments) // Process arguments for the command

            // Add parameters to the setup if they are valid and the command has been used
            if (params.arguments.nonEmpty && command.hasSeen) {
                setup.addArgAndParam(command.keyStr, params)
            }

            // Handle commands that fail validation
            if (command.hasFailed) {
                invalidInput(command.keyStr)
            }

            // If the help command is invoked, display help and exit
            if (command.keyStr == CommandUtils.helpCommandName && command.hasSeen) {
                runHelp()
                return new CommandArgumentsCollection() // Return an empty collection after help
            }

            // Check if the input file command is present and valid
            if (command.keyStr == CommandUtils.inputFileCommandName && command.hasSeen && !command.hasFailed) {
                inputFileIncluded = true
            }
        }

        // Ensure an input file is provided and the setup contains valid arguments
        if (!inputFileIncluded || setup.argsEmpty) {
            invalidInput()
        }

        setup // Return the processed command arguments collection
    }
}
