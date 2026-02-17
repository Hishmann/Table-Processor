package Modules.Commands.Variants

import Models.CommandModels.CommandArguments
import Modules.Commands.Command

/**
 * A class representing a toggle command implementation of the `Command` abstract class.
 *
 * The `toggleCommand` is used for commands that act as toggles (on/off switches) without requiring
 * additional input arguments. It processes command-line arguments to detect the presence of
 * the command key and returns an empty argument list when triggered.
 *
 * @param keyStr The primary key string for the command (default: empty string).
 * @param keyStr2 The secondary key string for the command (default: empty string).
 * @param helpStr A help string providing usage information (default: empty string).
 * @param inputCount The number of arguments expected for the command (default: 0, as toggles require no arguments).
 */
class toggleCommand(
                       override val keyStr: String = "",
                       override val keyStr2: String = "",
                       override val helpStr: String = "",
                       override val inputCount: Int = 0
                   ) extends Command {

    /**
     * Creates a deep copy of the current `toggleCommand` instance.
     * @return A new `toggleCommand` object with the same parameters as the current instance.
     */
    def deepCopy: toggleCommand = new toggleCommand(keyStr, keyStr2, helpStr, inputCount)
    /**
     * Processes the command-line arguments to detect the presence of the toggle command.
     * @param args A list of strings representing command-line arguments.
     * @return A `CommandArguments` object containing an empty list with an empty string if the toggle command is triggered,
     *         or an empty list if the command is not present or if an error occurs.
     */
    override def processCommand(args: List[String]): CommandArguments = {
        var i: Int = 0

        // Iterate through the list of arguments
        while (i < args.length) {
            // Check for a match with the key strings
            if (args(i) == "--" + keyStr || args(i) == "-" + keyStr2) {
                seen = 1 // Mark the command as seen

                // Check for unexpected extra arguments after the toggle
                if (i + inputCount + 1 < args.length &&
                    !args(i + inputCount + 1).startsWith("--") &&
                    !args(i + inputCount + 1).startsWith("-")) {
                    failed = 1 // Mark the command as failed
                    return CommandArguments(List()) // Return an empty result
                }

                // Return an empty argument list to indicate the toggle was triggered
                return CommandArguments(List(""))
            }
            i += 1 // Move to the next argument
        }

        // Return an empty result if the command was not found
        return CommandArguments(List())
    }
}
