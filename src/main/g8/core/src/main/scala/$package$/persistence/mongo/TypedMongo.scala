package $package$
package persistence.mongo

import java.util.UUID

import $package$.domain.models._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.MongoConversionHelper
import org.bson.types.Binary
import org.bson.{BSON, BsonBinarySubType, Transformer}
import org.joda.time.DateTime


object JavaUUIDSerializer extends MongoConversionHelper {

  import util.Imports._

  private val transformer = new Transformer {
    override def transform(o: AnyRef): AnyRef = o match {
      case j: UUID => new Binary(BsonBinarySubType.UUID_STANDARD, j.toByteArray)
      case _ => o
    }
  }

  override def register(): Unit = {
    log.debug("Setting up JavaUUIDSerializer")
    BSON.addEncodingHook(classOf[UUID], transformer)
    super.register()
  }
}

trait MongoDeserializers {

  def deserializer[A](implicit deserializer: MongoDeserializer[A]) = deserializer

  protected def inner[A: MongoDeserializer](o: DBObject, field: String): A = deserializer[A].apply(o.as[DBObject](field))

  implicit object TestDeserializer extends MongoDeserializer[Test] {
    def apply(o: DBObject) = {
      Test(
        o.getAs[UUID]("_id").orNull,
        o.getAs[String]("name").orNull
      )
    }
  }
}

trait MongoSerializers {

  def serializer[A](implicit serializer: MongoSerializer[A]) = serializer

  implicit object ScoreDetailsSerializer extends MongoSerializer[Test] {
    def apply(o: Test) = {
      MongoDBObject(
        "_id" -> o.id,
        "name" -> o.name
      )
    }
  }
}

trait TypedMongo {

  import Imports._

  def serialize[T: MongoSerializer](value: T): DBObject = serializer[T].apply(value)

  def deserialize[T: MongoDeserializer](value: DBObject): T = deserializer[T].apply(value)

  def mapper[T: MongoDeserializer]: MongoDeserializer[T] = { (value: DBObject) => deserialize[T](value) }

}
