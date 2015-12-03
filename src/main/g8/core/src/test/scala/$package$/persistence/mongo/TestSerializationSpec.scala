package $package$.persistence.mongo

import $package$.domain.models._
import $package$.persistence.mongo.Imports._
import $package$.util.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.DateTime
import org.scalatest._

import scala.language.reflectiveCalls

class TestSerializationSpec extends FlatSpec with Matchers with MongoSpecCommon {

  val testData = Test(
    generateId,
    "Test"
  )

  // region Deserialization specs

  "TestDeserializer" should "be able to deserialize a complete Test model" in {
    val builder = fixture.builder

    val o = build[Test](builder,
      "_id" -> testData.id,
      "name" -> testData.name
    )

    o should be(testData)

  }

  // endregion

  // region Serialization specs

  "TestSerializer" should "be able to serialize a complete Test model" in {

    val o = serialize(testData)

    o should be(MongoDBObject(
      "_id" -> testData.id,
      "name" -> testData.name
    ))
  }

  // endregion

}
