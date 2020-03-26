package enumeratum.mongodb

import enumeratum.values._

import org.bson.codecs.Codec
import org.bson.codecs.configuration.{CodecProvider, CodecRegistry}

import scala.reflect.ClassTag

private[mongodb] trait MongoDBValueEnum[ValueType, EntryType <: ValueEnumEntry[ValueType]] {
  enum: ValueEnum[ValueType, EntryType] =>

  final lazy val bsonCodecProvider: CodecProvider = new CodecProvider {
    override final def get[T](clazz: Class[T], registry: CodecRegistry): Codec[T] =
      if (enum.values.exists(clazz.isInstance)) {
        createCodec(ClassTag[EntryType](clazz)).asInstanceOf[Codec[T]]
      } else null
  }

  protected def createCodec(
      implicit classTag: ClassTag[EntryType]
  ): ValueEnumCodec[ValueType, EntryType]

}

trait MongoDBIntEnum[EntryType <: ValueEnumEntry[Int]] extends MongoDBValueEnum[Int, EntryType] {
  enum: ValueEnum[Int, EntryType] =>
  final def createCodec(implicit classTag: ClassTag[EntryType]): ValueEnumCodec[Int, EntryType] =
    new IntValueEnumCodec[EntryType](enum)
}

trait MongoDBLongEnum[EntryType <: ValueEnumEntry[Long]] extends MongoDBValueEnum[Long, EntryType] {
  enum: ValueEnum[Long, EntryType] =>
  final def createCodec(implicit classTag: ClassTag[EntryType]): ValueEnumCodec[Long, EntryType] =
    new LongValueEnumCodec[EntryType](enum)
}

trait MongoDBShortEnum[EntryType <: ValueEnumEntry[Short]]
    extends MongoDBValueEnum[Short, EntryType] { enum: ValueEnum[Short, EntryType] =>
  final def createCodec(implicit classTag: ClassTag[EntryType]): ValueEnumCodec[Short, EntryType] =
    new ShortValueEnumCodec[EntryType](enum)
}

trait MongoDBByteEnum[EntryType <: ValueEnumEntry[Byte]] extends MongoDBValueEnum[Byte, EntryType] {
  enum: ValueEnum[Byte, EntryType] =>
  final def createCodec(implicit classTag: ClassTag[EntryType]): ValueEnumCodec[Byte, EntryType] =
    new ByteValueEnumCodec[EntryType](enum)
}

trait MongoDBStringEnum[EntryType <: ValueEnumEntry[String]]
    extends MongoDBValueEnum[String, EntryType] { enum: ValueEnum[String, EntryType] =>
  final def createCodec(implicit classTag: ClassTag[EntryType]): ValueEnumCodec[String, EntryType] =
    new StringValueEnumCodec[EntryType](enum)
}

trait MongoDBCharEnum[EntryType <: ValueEnumEntry[Char]] extends MongoDBValueEnum[Char, EntryType] {
  enum: ValueEnum[Char, EntryType] =>
  final def createCodec(implicit classTag: ClassTag[EntryType]): ValueEnumCodec[Char, EntryType] =
    new CharValueEnumCodec[EntryType](enum)
}
