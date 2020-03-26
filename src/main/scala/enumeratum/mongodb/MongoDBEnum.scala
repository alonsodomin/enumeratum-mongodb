package enumeratum.mongodb

import enumeratum._
import org.bson.codecs.Codec
import org.bson.codecs.configuration.{CodecProvider, CodecRegistry}

import scala.reflect.ClassTag

trait MongoDBEnum[A <: EnumEntry] { enum: Enum[A] =>

  final lazy val bsonCodecProvider: CodecProvider = new CodecProvider {
    override final def get[T](clazz: Class[T], registry: CodecRegistry): Codec[T] =
      if (enum.values.exists(clazz.isInstance)) {
        new EnumCodec[A](enum)(ClassTag[A](clazz)).asInstanceOf[Codec[T]]
      } else null
  }

}
