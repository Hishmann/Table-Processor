package Table.TableModifiers
import Table.TableModifiers.Filters.{Filters, ValueFilters, CellFilters}
import Table.TableModifiers.Filters.filterInputHelpers.{operationValueFilterMap, operationCellFilterMap}

class ColAndFilter(val column: String, val filter: Filters[_]) {}

class FilterApplied {
    private var filterSettings: List[ColAndFilter] = List()
    def getFilterSetting: List[ColAndFilter] = filterSettings

    def addValueFilterToApply(column:String, operation: String, number: String): Unit  = {
        val filter: ValueFilters = operationValueFilterMap.get(operation) match {
            case Some(value) => value(number.toInt)
            case None => throw new RuntimeException(s"No $operation filter implementation available")
        }
        filterSettings = filterSettings :+ new ColAndFilter(column, filter)
    }
    def addCellFilterToApply(column:String, name: String): Unit = {
        val filter: CellFilters = operationCellFilterMap.get(name) match {
            case Some(value) => value
            case None => throw new RuntimeException(s"No $name filter implementation available")
        }
        filterSettings = filterSettings :+ new ColAndFilter(column, filter)
    }
}
