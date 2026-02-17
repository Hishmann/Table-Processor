package Modules.Commands

import Models.CommandModels.CommandArguments

/**
 * An abstract class representing a command in a system.
 *
 * This class serves as a base for defining commands with specific functionality.
 * It includes properties for command metadata, methods for tracking command usage,
 * and abstract methods that must be implemented by subclasses.
 *
 * @param keyStr A primary key string identifying the command (default: empty string).
 * @param keyStr2 A secondary key string for the command (default: empty string).
 * @param helpStr A help string providing a description or usage information for the command (default: empty string).
 * @param inputCount The expected number of input arguments for the command (default: 1).
 */
abstract class Command(
                          val keyStr: String = "",
                          val keyStr2: String = "",
                          val helpStr: String = "",
                          val inputCount: Int = 1
                      ) {
    // A protected variable indicating if the command has failed (1 for failure, 0 otherwise).
    protected var failed: Int = 0
    // A protected variable indicating if the command has been seen or used (1 for seen, 0 otherwise).
    protected var seen: Int = 0
    /**
     * Checks if the command execution has failed.
     * @return `true` if the command failed, `false` otherwise.
     */
    def hasFailed: Boolean = failed == 1
    /**
     * Checks if the command has been seen.
     * @return `true` if the command has been seen, `false` otherwise.
     */
    def hasSeen: Boolean = seen == 1
    /**
     * Creates a deep copy of the command instance.
     * @return A new `command` object that is a deep copy of the current instance.
     */
    def deepCopy: Command
    /**
     * Processes the command with the given arguments.
     * @param args A list of strings representing the input arguments for the command.
     * @return A `CommandArguments` object encapsulating the processed arguments.
     */
    def processCommand(args: List[String]): CommandArguments
}