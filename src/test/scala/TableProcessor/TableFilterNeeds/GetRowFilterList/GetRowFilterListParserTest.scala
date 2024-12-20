package TableProcessor.TableFilterNeeds.GetRowFilterList

import Models.CommandModels.{CommandArguments, CommandArgumentsCollection}
import TableProcessor.CommandUtils.CommandUtils
import org.scalatest.FunSuite
import TableProcessor.TableFilterNeeds.GetRowFilterList._
import Models.FilterModels.Filters.CellFilters.{EmptyFilter, NonEmptyFilter}
import Models.FilterModels.Table.TableRowFilterContext
import Models.FilterModels.Filters.CellFilters.ValueFilters.{
    GreaterThanFilter, GreaterOrEqualFilter, LessOrEqualFilter, LessThanFilter, EqualFilter, NotEqualFilter
}

class GetRowFilterListParserTest extends FunSuite {
    val CA: CommandArgumentsCollection = CommandArgumentsCollection()
    CA.addArgAndParam(CommandUtils.filterCommandName, CommandArguments(List("A",">","5","B","<","10")))
    CA.addArgAndParam(CommandUtils.filterEmptyCommandName, CommandArguments(List("A")))
    CA.addArgAndParam(CommandUtils.filterNotEmptyCommandName, CommandArguments(List("A","B")))

    val emptyGetRowFilterListParser = new EmptyGetRowFilterListParser(CA)
    val valueGetRowFilterListParser = new ValueGetRowFilterListParser(CA)

    val test1: List[TableRowFilterContext] = List(
        new TableRowFilterContext("A", EmptyFilter()),
        new TableRowFilterContext("A", NonEmptyFilter()),
        new TableRowFilterContext("B", NonEmptyFilter()),
        new TableRowFilterContext("A", GreaterThanFilter(5)),
        new TableRowFilterContext("B", LessThanFilter(10))
    )
    test("Getting filter Context") {
        val result = emptyGetRowFilterListParser.getFilters ++ valueGetRowFilterListParser.getFilters
        assert(result == test1)
    }
}
