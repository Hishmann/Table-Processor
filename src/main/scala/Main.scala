import CommandLineInterface._
import commands._
import LoadData.LD
import LoadData.formats.{inputFormatParameters, loadFormatParameters}
import Table.Table
import _root_.Table.TableFormulaEvaluator.TableEvaluator
import _root_.Table.TableModifiers.{FilterApplied, TableFilter}
import _root_.Table.TableModifiers.Filters.filterInputHelpers.{isCellFilterFormat, isValueFilterFormat}
import _root_.Table.TableModifiers.Filters.emptyFilter

package Main {}

object Main extends App {

    private def customMain(args: Array[String]): Unit = {

        val commandArgs = new CommandArgs(Map()) // Data structure to store the arguments

        // Needed commands initialized
        val inputCommand = new inputFileCommand()
        val helpCommand = new helpCommand()
        val separatorCommand = new separatorCommand()
        val outputSeparator = new outputSeparatorCommand()
        val rangeCommand = new rangeCommand()
        val filterIsEmptyCommand = new filterIsEmptyCommand()
        val filterCommand = new filterCommand()

        // Commands to read and collect input for
        val commandList = List(
            inputCommand,
            helpCommand,
            separatorCommand,
            outputSeparator,
            rangeCommand,
            filterIsEmptyCommand,
            filterCommand
        )

        // Takes the commands, command argument data structure, and Argument list from terminal
        val CLI = new CLI(args.toList, commandArgs, commandList);
        CLI.runCommands() // Runs through the commands and updates the commandArgs data structure

        // inputFormatParameters is a class that stores all the necessary arguments for the different
        // input formats which are responsible for the method of the file source
        val inputFormatParams = new inputFormatParameters(
            CsvSeparator = commandArgs.valueFromKeyOrElse(separatorCommand.keyStrReturn).head
        )

        // loadFormatParameters is a class that stores all the necessary arguments for the different
        // load formats which are responsible for the method of reading the file depending on file type
        val loadFormatParams = new loadFormatParameters(
            inputFileDest = commandArgs.valueFromKeyOrElse(inputCommand.keyStrReturn).head
        )

        // Takes the above parameters and then gets the data into a form of List[List[String]]
        val LD = new LD(inputFormatParams, loadFormatParams)
        val processedFile: List[List[String]] = LD.load()

        // Table data structure that represents the table to be utilized for manipulation and
        // final output
        val table = new Table(processedFile)
        val tableEval = new TableEvaluator(table) // Table Evaluator that takes a table and evaluates all formulas
        table.setData(tableEval.evaluateTable().getData) // updating the table with the new evaluated data

        println(table.printTable())

        // Data structure that stores the filters to be used later
        val filtersApplied = new FilterApplied()
        // filters collected accordingly by the command that represents them and then stored
        // in the filterApplied class depending on the type of Filter
        val filterArgs = commandArgs.valueFromKeyOrElse(filterCommand.keyStrReturn)
        val filterEmptyArgs = commandArgs.valueFromKeyOrElse(filterIsEmptyCommand.keyStrReturn)
        // Code for ValueFilters
        if (filterArgs.head.nonEmpty) {
            for (i <- filterArgs.grouped(3).toList) {
                if (isValueFilterFormat(i)) {
                    filtersApplied.addValueFilterToApply(i(0), i(1), i(2))
                } else {
                    throw new RuntimeException("Filter arguments are incorrect")
                }
            }
        }

        // Code for empty filter (CellFilter)
        if (filterEmptyArgs.head.nonEmpty) {
            for (i <- filterEmptyArgs.grouped(1).toList) {
                if (isCellFilterFormat(i)) {
                    filtersApplied.addCellFilterToApply(i(0), new emptyFilter().name)
                } else {
                    throw new RuntimeException("Empty Filter arguments are incorrect")
                }
            }
        }

        // TableFilterer that takes the filters and filters the table when called with a table
        val tableFilterer = new TableFilter(filtersApplied)
        tableFilterer.filteredTable(table).printTable()

    }

    customMain(args)
}