package LoadData.ParseMethod.TableLoader

import LoadData.ParseMethod.ParseLoader
import LoadData.SourceLoader.SourceLoader
import Models.Table.Table
import TableCreator.TableCreator

class XMLTableLoader(delegate: SourceLoader, creator: TableCreator[List[List[String]]])
    extends TableLoader[List[List[String]]](delegate, creator) {
    def parse: Table = {Table()}
}
