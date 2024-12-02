package TableEvaluator

import Models.Table.Table

trait TableEvaluator {
    def evaluate(input: Table): Table
}
