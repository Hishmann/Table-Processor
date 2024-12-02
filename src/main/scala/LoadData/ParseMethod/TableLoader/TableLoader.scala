package LoadData.ParseMethod.TableLoader

import Models.Table.Table
import LoadData.ParseMethod.ParseLoader
import LoadData.SourceLoader.SourceLoader
import TableCreator.TableCreator

abstract class TableLoader[T](override val delegate: SourceLoader,
                           val creator: TableCreator[T]
                          ) extends ParseLoader[Table](delegate) {
}
