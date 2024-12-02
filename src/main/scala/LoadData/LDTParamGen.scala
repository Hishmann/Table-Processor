package LoadData

import Models.CommandArguments
import Models.Parameters.LoadDataTableParameters
import CommandUtils.CommandUtils

class LDTParamGen(val CA: CommandArguments) {
    def generateLDTParamaters: LoadDataTableParameters = {
        val inputFileArg: String = CA.valueFromArgOrEmpty(CommandUtils.inputFileCommandName) match {
            case Some(value) => value.head
            case None => throw new RuntimeException("Program cannot run without an Input instruction")
        }
        val sepArg: String = CA.valueFromArgOrEmpty(CommandUtils.separatorCommandName) match {
            case Some(value) => value.head
            case None => ","
        }
        val formatArg: String =  CA.valueFromArgOrEmpty(CommandUtils.formatCommandName) match {
            case Some(value) => value.head
            case None => "csv"
        }
        LoadDataTableParameters(inputFileArg, sepArg, formatArg)
    }
}
