package Models.RangeModels

/**
 * A case class representing a range context defined by a start and stop value for The range table modifier.
 *
 * This class is typically used to encapsulate a range of values, such as a range of strings,
 * which is utilized in the table range modifier.
 *
 * @param start The starting value of the range.
 * @param stop The ending value of the range.
 */
case class RangeContext(val start: String, val stop: String)
