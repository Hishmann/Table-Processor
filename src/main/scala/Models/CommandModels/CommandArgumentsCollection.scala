package Models.CommandModels

/**
 * A class representing a collection of command-line arguments, organized in a map
 * where the key is a string (e.g. command name) and the value is a `CommandArguments` object.
 */
case class CommandArgumentsCollection() {

    // A private map to store the argument collection, where the key is the command name
    // and the value is a `CommandArguments` object.
    private var argumentCollection: Map[String, CommandArguments] = Map()

    /**
     * Checks if the argument collection is empty.
     *
     * @return `true` if the collection is empty, `false` otherwise.
     */
    def argsEmpty: Boolean = { argumentCollection.isEmpty }

    /**
     * Compares this argument collection with another for equality.
     *
     * @param test The `CommandArgumentsCollection` to compare with.
     * @return `true` if both collections have identical mappings, `false` otherwise.
     */
    def checkEquality(test: CommandArgumentsCollection): Boolean = {
        argumentCollection == test.argumentCollection
    }

    /**
     * Retrieves the value associated with a given Command key from the collection, if it exists.
     *
     * @param key Command key to look up in the argument collection.
     * @return An `Option` containing the associated `CommandArguments` if the key exists,
     *         or `None` if the key does not exist.
     */
    def argFromCommOrEmpty(key: String): Option[CommandArguments] = {
        argumentCollection.get(key)
    }

    /**
     * Adds a new command and its corresponding arguments to the collection.
     * If the key already exists, it will be overwritten.
     *
     * @param name The name of the Command (key).
     * @param params The `CommandArguments` object containing the parameters for the argument.
     */
    def addArgAndParam(name: String, params: CommandArguments): Unit = {
        argumentCollection = argumentCollection ++ Map(name -> params)
    }

    def printParam(): Unit = {
        println(argumentCollection)
    }
}

