package Table.TableModifiers
import Table.Table
import Table.TableModifiers.FilterApplied
import Table.TableModifiers.Filters.{Filters, ValueFilters, CellFilters}
import  Table.{IntCell, EmptyCell}

class TableFilter(val params: FilterApplied ) {
    def filteredTable(table: Table): Table = {
        val res = new Table(List())
        var rowsToRemove: List[Int] = List()

        for (setting <- params.getFilterSetting) {
            val col = table.columnToIndex(setting.column)
            setting.filter match {
                case vf: ValueFilters => {
                    for (i <- table.getRows) {
                        table.getData(i)(col) match {
                            case ic: IntCell => {
                                if (vf.doFilter(ic.getCellContent)) {
                                    if (!rowsToRemove.contains(i)) {
                                        rowsToRemove = rowsToRemove :+ i // Append element
                                    }
                                }
                            }
                            case ec: EmptyCell => None
                            case _ => throw new RuntimeException("Table has not been evaluated before filtering")
                        }
                    }
                }
                case cf: CellFilters => {
                    for (i <- table.getRows) {
                        table.getData(i)(col) match {
                            case ec: EmptyCell => {
                                if (cf.doFilter(ec)) {
                                    if (!rowsToRemove.contains(i)) {
                                        rowsToRemove = rowsToRemove :+ i // Append element
                                    }
                                }
                            }
                            case ic: IntCell => None
                            case _ => throw new RuntimeException("Table has not been evaluated before filtering")
                        }
                    }
                }
                case _ => None
            }
        }

        res.setData(table.getData)
        res.setRows(table.getRows.diff(rowsToRemove))
        res
    }
}
