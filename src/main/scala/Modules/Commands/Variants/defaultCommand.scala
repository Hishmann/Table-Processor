package Modules.Commands.Variants

import Models.CommandModels.CommandArguments
import Modules.Commands.Command

/**
 * A class representing a default implementation of the `Command` abstract class.
 *
 * The `defaultCommand` is designed to process command-line arguments based on a specified
 * key string (`keyStr`), a secondary key string (`keyStr2`), and an expected number of input arguments (`inputCount`).
 *
 * @param keyStr The primary key string for the command (default: empty string).
 * @param keyStr2 The secondary key string for the command (default: empty string).
 * @param helpStr A help string providing usage information (default: empty string).
 * @param inputCount The number of arguments expected for the command (default: 1).
 */
class defaultCommand(
                        override val keyStr: String = "",
                        override val keyStr2: String = "",
                        override val helpStr: String = "",
                        override val inputCount: Int = 1
                    ) extends Command {

    /**
     * Creates a deep copy of the current `defaultCommand` instance.
     * @return A new `defaultCommand` object with the same parameters as the current instance.
     */
    def deepCopy: defaultCommand = new defaultCommand(keyStr, keyStr2, helpStr, inputCount)
    /**
     * Processes the command-line arguments and checks for a match with the key strings.
     * If a match is found, it attempts to extract the expected number of arguments.
     *
     * @param args A list of strings representing command-line arguments.
     * @return A `CommandArguments` object containing the extracted arguments if successful, or an empty list if failed.
     */
    def processCommand(args: List[String]): CommandArguments = {
        var i: Int = 0
        var tempArgStr: List[String] = List()

        // Iterate through the list of arguments
        while (i < args.length) {
            // Check for a match with the key strings
            if (args(i) == ("--" + keyStr) || args(i) == ("-" + keyStr2)) {
                seen = 1 // Mark the command as seen

                // Attempt to extract the expected number of arguments
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

                // Validate if the correct number of arguments were extracted
                if (tempArgStr.length == inputCount) {
                    return CommandArguments(tempArgStr)
                } else {
                    failed = 1 // Mark the command as failed
                    return CommandArguments(List()) // Return an empty result
                }
            }
            i += 1 // Move to the next argument
        }

        // Return an empty result if no match was found
        return CommandArguments(List())
    }
}

