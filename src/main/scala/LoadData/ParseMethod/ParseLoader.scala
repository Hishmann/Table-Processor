package LoadData.ParseMethod

import LoadData.SourceLoader.SourceLoader
import TableCreator.TableCreator

abstract class ParseLoader[T](val delegate: SourceLoader) {
    def parse: T
}
