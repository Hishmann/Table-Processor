package TableProcessor.TableRangeNeeds

import org.scalatest.FunSuite
import Models.CommandModels.{CommandArguments, CommandArgumentsCollection}
import Models.RangeModels.RangeContext
import Models.Table.{Table, TextTable}
import TableProcessor.CommandUtils.CommandUtils
import Modules.TableFunctions.TableCreator.TableCreatorFromText

class GetRangeContextTest extends FunSuite {

    val EmptyTable: Table = Table()
    val NonEmptyTable: Table = new TableCreatorFromText().getTable(
        new TextTable(
            List(
                List("1", "4", "020"),
                List("10000", "30", "9"),
                List("2", "", "1")
            ))
    )
    val CA: CommandArgumentsCollection = CommandArgumentsCollection()
    CA.addArgAndParam(CommandUtils.rangeCommandName, CommandArguments(List("A1","B2")))

    val CA2: CommandArgumentsCollection = CommandArgumentsCollection()

    val GetCon = new GetRangeContext(CA)
    val GetCon2 = new GetRangeContext(CA2)

    test("Basic range context acquires") {
        assert(GetCon.getRange(EmptyTable) == RangeContext("A1","B2"))
        assert(GetCon.getRange(NonEmptyTable) == RangeContext("A1","B2"))
        assert(GetCon2.getRange(EmptyTable) == RangeContext("A1","A1"))
        assert(GetCon2.getRange(NonEmptyTable) == RangeContext("A1","C3"))
    }

}
