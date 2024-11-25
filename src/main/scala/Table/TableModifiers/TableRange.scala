package Table.TableModifiers
import Table.Table

class TableRange(val start: String, val stop: String) {

    def limitRange(table: Table): Table = {
        val res = new Table(List())
        var newRows = table.getRows
        var newColumns = table.getColumns

        val formatRegex = "^[A-Z][0-9]+$".r
        if (formatRegex.matches(start) && formatRegex.matches(stop)) {
            val startColNumber = table.columnToIndex(start.takeWhile(_.isLetter))
            val startRowNumber = start.dropWhile(_.isLetter).toInt
            val stopColNumber = table.columnToIndex(stop.takeWhile(_.isLetter))
            val stopRowNumber = stop.dropWhile(_.isLetter).toInt

            val result = compareCells(startRowNumber, startColNumber, stopRowNumber, stopColNumber)
            result match {
                case 1 => {
                    throw new RuntimeException(s"Invalid input format for Range as  $start is higher than $stop")
                }
                case -1 => {
                    newRows = newRows.filter(elem => elem >= startRowNumber && elem <= stopRowNumber )
                    newColumns = newColumns.filter(elem => elem >= startColNumber-1 && elem <= stopColNumber-1 )
                }
                case 0 => {
                    println(s"$start is equal to $stop")
                    newColumns = List(startColNumber)
                    newRows = List(startRowNumber - 1)
                }
            }
        } else {
            throw new RuntimeException("Invalid input format for Range. Please provide inputs in the format 'A1', 'B2', etc.")
        }
        res.setData(table.getData)
        res.setRows(newRows)
        res.setColumns(newColumns)
        res
    }

    def compareCells(r1: Int, c1: Int, r2: Int, c2: Int): Int = {
        // Compare column numbers first, then rows
        if (c1 > c2) 1
        else if (c1 < c2) -1
        else {
            if (r1 > r2) 1
            else if (r1 < r2) -1
            else 0
        }
    }
}
