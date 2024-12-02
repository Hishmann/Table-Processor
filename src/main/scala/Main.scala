import CommandLineInterface.CLI
import CommandUtils.CommandUtils
import Models.CommandArguments

import LoadData.LDTParamGen
import LoadData.LoadDataTable
import Models.Parameters.LoadDataTableParameters

import Models.Table.Table
import TableEvaluator.FormulaEvaluator.TableFormulaEvaluator

package Main {}

object Main extends App {

    private def customMain(args: Array[String]): Unit = {

        val CLI = new CLI(args.toList, CommandUtils.commandList)
        val CA: CommandArguments = CLI.runCommands()

        val LDTParam = new LDTParamGen(CA).generateLDTParamaters
        val LD = new LoadDataTable(LDTParam)

        var table = LD.load
        val TBFeval = new TableFormulaEvaluator
        table = TBFeval.evaluate(table)

    }

    customMain(args)
}