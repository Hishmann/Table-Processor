package TableUtils

import Models.Table.Cells.Cell

object TableUtils {

    def columnToIndex(column: String): Int = {
        column.toUpperCase.foldLeft(0) { (acc, char) =>
            acc * 26 + (char - 'A')
        }
    }

}
