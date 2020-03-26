package enumeratum.mongodb

import enumeratum._
import enumeratum.values._

case class Shirt(description: String, size: ShirtSize, sizeShort: CharShirtSize)

sealed trait ShirtSize extends EnumEntry
object ShirtSize extends Enum[ShirtSize] with MongoDBEnum[ShirtSize] {
  case object Small  extends ShirtSize
  case object Medium extends ShirtSize
  case object Large  extends ShirtSize

  val values = findValues
}

abstract sealed class CharShirtSize(val value: Char) extends CharEnumEntry
object CharShirtSize extends CharEnum[CharShirtSize] with MongoDBCharEnum[CharShirtSize] {
  case object Small  extends CharShirtSize('S')
  case object Medium extends CharShirtSize('M')
  case object Large  extends CharShirtSize('L')

  val values = findValues
}