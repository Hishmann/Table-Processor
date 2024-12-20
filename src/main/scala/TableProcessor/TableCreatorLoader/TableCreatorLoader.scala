package TableProcessor.TableCreatorLoader

// Import necessary modules and models for table creation
import Models.Table.TextTable
import Modules.TableFunctions.TableCreator.{TableCreator, TableCreatorFromText}

/**
 * TableCreatorLoader is responsible for providing an instance of a TableCreator.
 * It facilitates creating a table from a given text-based table representation.
 */
class TableCreatorLoader {

    /**
     * Returns an instance of TableCreator specialized for handling TextTable objects.
     * @return A TableCreator[TextTable] instance for creating tables from text-based data.
     */
    def getLoader: TableCreator[TextTable] = {
        new TableCreatorFromText // Instantiate and return a TableCreatorFromText
    }
}
