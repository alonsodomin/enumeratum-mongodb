package enumeratum.mongodb

import enumeratum._

case class Shirt(description: String, size: ShirtSize)

sealed trait ShirtSize extends EnumEntry
object ShirtSize extends Enum[ShirtSize] with MongoDBEnum[ShirtSize] {
  case object Small extends ShirtSize
  case object Medium extends ShirtSize
  case object Large extends ShirtSize

  val values = findValues
}