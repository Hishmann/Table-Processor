package Modules.TableFunctions.TableEvaluator

import Models.Table.Table

/**
 * A trait for evaluating and transforming a `Table`.
 *
 * The `TableEvaluator` trait defines a contract for performing evaluations or computations
 * on a `Table` object, potentially modifying or transforming it based on specific logic.
 */
trait TableEvaluator {

    /**
     * Evaluates a `Table` and returns the resulting `Table`.
     *
     * @param input The input `Table` to be evaluated.
     * @return A new `Table` object resulting from the evaluation.
     */
    def evaluate(input: Table): Table
}
