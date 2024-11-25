package Table.TableModifiers.Filters
import Table.{Cell, EmptyCell}

trait Filters[T] {
    val name: String
    def doFilter(input: T): Boolean
    // Define equality based on `name` for all Filters
    override def equals(obj: Any): Boolean = obj match {
        case that: Filters[_] => this.name == that.name
        case _ => false
    }
    override def hashCode(): Int = name.hashCode
}

abstract class ValueFilters(val value: Int) extends Filters[Int] {
    def getValue: Int = value
    def doFilter(input: Int): Boolean
    // Extend equality to include `value`
    override def equals(obj: Any): Boolean = obj match {
        case that: ValueFilters =>
            super.equals(that) && this.value == that.value
        case _ => false
    }
    override def hashCode(): Int = {
        31 * super.hashCode() + value
    }
}

class greaterThanFilter(value: Int = 0) extends ValueFilters(value) {
    override val name: String = ">"
    override def doFilter(input: Int): Boolean = input > value
}

abstract class CellFilters extends Filters[Cell[_]] {
}

class emptyFilter extends CellFilters {
    override val name: String = "empty"
    def doFilter(input: Cell[_]): Boolean = input match {
        case cell: EmptyCell => true
        case _ => false
    }
}

object filterInputHelpers {

    val operationValueFilterMap: Map[String, Int => ValueFilters] = Map(
        (new greaterThanFilter()).name -> ((a:Int) => new greaterThanFilter(a)),
    )

    def isValueFilterFormat(input: List[String]): Boolean = {
        val lettersRegex = "^[A-Z]+$".r        // Matches one or more uppercase letters.
        val operatorsRegex = ("^(" + operationValueFilterMap.keys.mkString("|") + ")$").r // Matches specific operators.
        val numbersRegex = "^[0-9]+$".r        // Matches one or more digits.
        input match {
            case List(letters, operator, number) if
                lettersRegex.matches(letters) &&
                    operatorsRegex.matches(operator) &&
                    numbersRegex.matches(number) =>
                true
            case _ =>
                false
        }
    }

    val operationCellFilterMap: Map[String, CellFilters] = Map(
        (new emptyFilter()).name -> new emptyFilter
    )

    def isCellFilterFormat(input: List[String]): Boolean = {
        val lettersRegex = "^[A-Z]+$".r
        input match {
            case List(lettersRegex()) =>
                true
            case _ =>
                false
        }
    }
}