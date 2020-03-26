package enumeratum.mongodb

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import com.github.simplyscala.MongoEmbedDatabase
import com.github.simplyscala.MongodProps
import org.scalatest.BeforeAndAfterEach
import org.mongodb.scala.MongoClient
import org.bson.codecs.configuration.CodecRegistries

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import com.mongodb.MongoClientSettings
import com.mongodb.ConnectionString
import org.mongodb.scala.bson.codecs.Macros

class EnumCodecSpec extends AnyFunSpec with Matchers with MongoEmbedDatabase {

  val codecRegistry =
    CodecRegistries.fromRegistries(
      CodecRegistries.fromProviders(
        Macros.createCodecProvider[Shirt](),
        ShirtSize.bsonCodecProvider,
        CharShirtSize.bsonCodecProvider
      ),
      MongoClientSettings.getDefaultCodecRegistry
    )

  describe("to MongoDB") {
    it("should serialize and deserialize enums") {
      val mongoDBPort = findFreePort()

      withEmbedMongoFixture(mongoDBPort) { _ =>
        val givenShirt = Shirt("Blue shirt", ShirtSize.Medium, CharShirtSize.Medium)

        val assertion = for {
          collection  <- collectionHandle(mongoDBPort)
          _           <- collection.insertOne(givenShirt).toFuture
          loadedShirt <- collection.find().first().toFuture
        } yield {
          loadedShirt shouldBe givenShirt
        }

        Await.result(assertion, 2.seconds)
      }

    }
  }

  private def collectionHandle(port: Int) = {
    val clientSettings = MongoClientSettings
      .builder()
      .codecRegistry(codecRegistry)
      .applyConnectionString(new ConnectionString(s"mongodb://localhost:$port"))
      .build()

    val mongoClient = MongoClient(clientSettings)
    val database    = mongoClient.getDatabase("EnumCodeSpec")

    database
      .createCollection("shirts")
      .toFuture
      .map(_ => database.getCollection[Shirt]("shirts"))
  }

}
