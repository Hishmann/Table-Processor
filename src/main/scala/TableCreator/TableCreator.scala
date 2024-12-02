package TableCreator

import Models.Table.Table

trait TableCreator[T] {
    def dataInitializer(inputData: T): Table
}
