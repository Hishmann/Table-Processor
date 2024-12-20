package Models.FilterModels

/**
 * A generic trait for filtering items of type `T`.
 *
 * This trait defines a contract for filtering operations, where a single input
 * of type `T` is evaluated against specific criteria, and a boolean result is returned.
 *
 * @tparam T The type of the input to be filtered.
 */
trait Filter[T] {
    /**
     * Applies a filter to the given input and determines whether it meets the criteria.
     *
     * @param input The input of type `T` to be evaluated.
     * @return `true` if the input passes the filter criteria, `false` otherwise.
     */
    def doFilter(input: T): Boolean
}

