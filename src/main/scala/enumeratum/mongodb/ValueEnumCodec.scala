package enumeratum.mongodb

import enumeratum.values._

import org.bson.{BsonReader, BsonWriter}
import org.bson.codecs.{Codec, EncoderContext, DecoderContext}

import scala.reflect.ClassTag
import org.bson.BsonBinary

private[mongodb] abstract class ValueEnumCodec[ValueType, EntryType <: ValueEnumEntry[ValueType]](
    factory: ValueEnum[ValueType, EntryType]
)(implicit classTag: ClassTag[EntryType])
    extends Codec[EntryType] {

  private lazy val _encoderClass =
    classTag.runtimeClass.asInstanceOf[Class[EntryType]]
  @inline def getEncoderClass(): Class[EntryType] = _encoderClass

}

final class IntValueEnumCodec[EntryType <: ValueEnumEntry[Int]: ClassTag] private[mongodb] (
    factory: ValueEnum[Int, EntryType]
) extends ValueEnumCodec[Int, EntryType](factory) {
  def encode(writer: BsonWriter, value: EntryType, encoderContext: EncoderContext): Unit =
    writer.writeInt32(value.value)

  def decode(reader: BsonReader, decoderContext: DecoderContext): EntryType =
    factory.withValue(reader.readInt32())
}

final class LongValueEnumCodec[EntryType <: ValueEnumEntry[Long]: ClassTag] private[mongodb] (
    factory: ValueEnum[Long, EntryType]
) extends ValueEnumCodec[Long, EntryType](factory) {
  def encode(writer: BsonWriter, value: EntryType, encoderContext: EncoderContext): Unit =
    writer.writeInt64(value.value)

  def decode(reader: BsonReader, decoderContext: DecoderContext): EntryType =
    factory.withValue(reader.readInt64())
}

final class ShortValueEnumCodec[EntryType <: ValueEnumEntry[Short]: ClassTag] private[mongodb] (
    factory: ValueEnum[Short, EntryType]
) extends ValueEnumCodec[Short, EntryType](factory) {
  def encode(writer: BsonWriter, value: EntryType, encoderContext: EncoderContext): Unit =
    writer.writeInt32(value.value.toInt)

  def decode(reader: BsonReader, decoderContext: DecoderContext): EntryType =
    factory.withValue(reader.readInt32().toShort)
}

final class ByteValueEnumCodec[EntryType <: ValueEnumEntry[Byte]: ClassTag] private[mongodb] (
    factory: ValueEnum[Byte, EntryType]
) extends ValueEnumCodec[Byte, EntryType](factory) {
  def encode(writer: BsonWriter, value: EntryType, encoderContext: EncoderContext): Unit =
    writer.writeInt32(value.value.toInt)

  def decode(reader: BsonReader, decoderContext: DecoderContext): EntryType =
    factory.withValue(reader.readInt32().toByte)
}

final class StringValueEnumCodec[EntryType <: ValueEnumEntry[String]: ClassTag] private[mongodb] (
    factory: ValueEnum[String, EntryType]
) extends ValueEnumCodec[String, EntryType](factory) {
  def encode(writer: BsonWriter, value: EntryType, encoderContext: EncoderContext): Unit =
    writer.writeString(value.value)

  def decode(reader: BsonReader, decoderContext: DecoderContext): EntryType =
    factory.withValue(reader.readString())
}

final class CharValueEnumCodec[EntryType <: ValueEnumEntry[Char]: ClassTag] private[mongodb] (
    factory: ValueEnum[Char, EntryType]
) extends ValueEnumCodec[Char, EntryType](factory) {
  def encode(writer: BsonWriter, value: EntryType, encoderContext: EncoderContext): Unit =
    writer.writeString(value.value.toString())

  def decode(reader: BsonReader, decoderContext: DecoderContext): EntryType =
    factory.withValue(reader.readString().charAt(0))
}
