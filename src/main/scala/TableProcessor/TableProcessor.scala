package TableProcessor

import TableProcessor.CommandLineInterface.CommandLineInterface
import CommandUtils.CommandUtils
import Models.CommandModels.CommandArgumentsCollection
import TableProcessor.FileToTextTableLoader.FileToTextTableLoader
import TableProcessor.SourceDataLoader.SourceDataLoader
import Models.Table.TextTable
import Modules.FileHandling.SourceToFileData.SourceToFileData
import Modules.FileHandling.FileDataToObject.FileDataToTextTable.FileDataToTextTable
import TableProcessor.TableCreatorLoader.TableCreatorLoader
import Modules.TableFunctions.TableCreator.TableCreator
import Models.Table.Table
import TableProcessor.TableEvaluatorLoader.TableEvaluatorLoader
import Modules.TableFunctions.TableEvaluator.TableEvaluator
import TableProcessor.TableFilterNeeds.TableFilterLoader
import TableProcessor.TableFilterNeeds.GetRowFilterList.{EmptyGetRowFilterListParser, ValueGetRowFilterListParser}
import Modules.TableFunctions.TableModifier.TableModifier
import Models.FilterModels.Table.TableRowFilterContext
import TableProcessor.TableRangeNeeds.{GetRangeContext, TableRangeLoader}
import Models.RangeModels.RangeContext
import TableProcessor.TablePrintLoader.{TablePrintExportLoader, TablePrintStdOutLoader}
import Modules.TableFunctions.TablePrinter.TablePrinter
import TableProcessor.TableStringToOutput.TableStringToOutputFile

/**
 * Main class for processing tables.
 * @param arguments List of command-line arguments provided to the program.
 */
class TableProcessor(arguments: List[String]) {

    /**
     * Executes the main workflow of the TableProcessor.
     */
    def run(): Unit = {

        // Initialize the Command Line Interface (CLI) with arguments and command list
        val CLI = new CommandLineInterface(arguments, CommandUtils.commandList)

        // Parse command arguments from the CLI
        val CA: CommandArgumentsCollection = CLI.runCommands()

        // Extract the input source file path from the command arguments
        val inputSource: String = CA.argFromCommOrEmpty(CommandUtils.inputFileCommandName) match {
            case Some(value) => value.arguments.head // Get the first argument value
            case None => throw new RuntimeException("Input source not specified.") // Error if input is missing
        }

        // Load the source data from the input file
        val sourceToFileData: SourceToFileData = new SourceDataLoader(inputSource).getSourceLoader

        // Convert file data to a textual table representation
        val fileDataToTextTable: FileDataToTextTable = new FileToTextTableLoader(CA).getTextTableLoader

        // Generate a TextTable object from the file data
        val textTable: TextTable = fileDataToTextTable.getResult(
            sourceToFileData.collectSource(inputSource)
        )

        // Create a Table object from the TextTable
        val tableCreator: TableCreator[TextTable] = new TableCreatorLoader().getLoader
        var table: Table = tableCreator.getTable(textTable)

        // Evaluate the table for any necessary modifications or validations
        val tableEvaluator: TableEvaluator = new TableEvaluatorLoader().getEvaluator
        table = tableEvaluator.evaluate(table)

        // Apply row-level filters to the table
        val tableFilter: TableModifier[TableRowFilterContext] = new TableFilterLoader().getLoader

        // Generate a list of row filter contexts from the command arguments
        val FilterContextList: List[TableRowFilterContext] =
            new EmptyGetRowFilterListParser(CA).getFilters ++ new ValueGetRowFilterListParser(CA).getFilters

        // Apply each filter context to modify the table
        for (i <- FilterContextList) {
            table = tableFilter.modify(table, i)
        }

        // Apply range modifications to the table
        val tableRange: TableModifier[RangeContext] = new TableRangeLoader().getLoader
        table = tableRange.modify(table, new GetRangeContext(CA).getRange(table))


        // Load printers for standard output and file export
        val tablePrinterStdOut: TablePrinter[String] = new TablePrintStdOutLoader(CA).getLoader
        val tablePrinterExport: TablePrinter[String] = new TablePrintExportLoader(CA).getLoader

        // Print the table to both the output file and standard output
        new TableStringToOutputFile(CA).outputIt(
            tablePrinterExport.print(table),
            tablePrinterStdOut.print(table)
        )
    }
}
