package enumeratum.mongodb

import enumeratum._

import org.bson.{BsonReader, BsonWriter}
import org.bson.codecs.{Codec, EncoderContext, DecoderContext}
import org.bson.codecs.configuration.CodecProvider

import scala.reflect.ClassTag

final class EnumCodec[A <: EnumEntry: ClassTag] private[mongodb] (factory: Enum[A]) extends Codec[A] {

  def encode(writer: BsonWriter, value: A, encoderContext: EncoderContext): Unit =
    writer.writeString(value.entryName)

  def decode(reader: BsonReader, decoderContext: DecoderContext): A =
    factory.withName(reader.readString())

  private lazy val _encoderClass =
    implicitly[ClassTag[A]].runtimeClass.asInstanceOf[Class[A]]
  @inline def getEncoderClass(): Class[A] = _encoderClass
  
}
