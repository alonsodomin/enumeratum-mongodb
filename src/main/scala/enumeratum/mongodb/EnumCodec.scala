package enumeratum.mongodb

import enumeratum._

import org.bson.{BsonReader, BsonWriter}
import org.bson.codecs.{Codec, EncoderContext, DecoderContext}

import scala.reflect.ClassTag

final class EnumCodec[A <: EnumEntry] private[mongodb] (factory: Enum[A])(
    implicit classTag: ClassTag[A]
) extends Codec[A] {

  def encode(writer: BsonWriter, value: A, encoderContext: EncoderContext): Unit =
    writer.writeString(value.entryName)

  def decode(reader: BsonReader, decoderContext: DecoderContext): A =
    factory.withName(reader.readString())

  private lazy val _encoderClass =
    classTag.runtimeClass.asInstanceOf[Class[A]]
  @inline def getEncoderClass(): Class[A] = _encoderClass

}
