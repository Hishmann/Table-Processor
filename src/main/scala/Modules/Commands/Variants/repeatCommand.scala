package Modules.Commands.Variants

import Models.CommandModels.CommandArguments
import Modules.Commands.Command

/**
 * A class representing a repeatable command implementation of the `Command` abstract class.
 *
 * The `repeatCommand` allows the command to be repeated multiple times in the arguments.
 * It processes the command-line arguments to count repetitions and extract the required
 * arguments for each repetition.
 *
 * @param keyStr The primary key string for the command (default: empty string).
 * @param keyStr2 The secondary key string for the command (default: empty string).
 * @param helpStr A help string providing usage information (default: empty string).
 * @param inputCount The number of arguments expected for each repetition of the command (default: 1).
 */
class repeatCommand(
                       override val keyStr: String = "",
                       override val keyStr2: String = "",
                       override val helpStr: String = "",
                       override val inputCount: Int = 1
                   ) extends Command {

    /**
     * Creates a deep copy of the current `repeatCommand` instance.
     * @return A new `repeatCommand` object with the same parameters as the current instance.
     */
    def deepCopy: repeatCommand = new repeatCommand(keyStr, keyStr2, helpStr, inputCount)
    /**
     * Processes the command-line arguments to handle multiple repetitions of the command.
     * @param args A list of strings representing command-line arguments.
     * @return A `CommandArguments` object containing the extracted arguments if successful,
     *         or an empty list if failed.
     */
    def processCommand(args: List[String]): CommandArguments = {
        var i: Int = 0
        var repeats: Int = 0 // Counter for the number of times the command is repeated
        var tempArgStr: List[String] = List()

        // Iterate through the list of arguments
        while (i < args.length) {
            // Check for a match with the key strings
            if (args(i) == "--" + keyStr || args(i) == "-" + keyStr2) {
                repeats += 1 // Increment the repeat count
                seen = 1 // Mark the command as seen

                // Extract arguments for the current repetition
                for (j <- 1 to inputCount) {
                    if (i + j < args.length && !args(i + j).startsWith("--") && !args(i + j).startsWith("-")) {
                        tempArgStr = tempArgStr ++ List(args(i + j))
                    }
                }

                // Check for extra unexpected arguments after the expected inputs
                if (i + inputCount + 1 < args.length &&
                    !args(i + inputCount + 1).startsWith("--") &&
                    !args(i + inputCount + 1).startsWith("-")) {
                    failed = 1 // Mark the command as failed
                    return CommandArguments(List()) // Return an empty result
                }

                // Validate if the number of extracted arguments matches the expected count for all repetitions
                if (tempArgStr.length < inputCount * repeats) {
                    failed = 1 // Mark the command as failed
                    return CommandArguments(List()) // Return an empty result
                }
            }
            i += 1 // Move to the next argument
        }

        // Return the collected arguments if the command was repeated
        if (repeats != 0) {
            return CommandArguments(tempArgStr)
        } else {
            return CommandArguments(List()) // Return an empty result if the command was not repeated
        }
    }
}
