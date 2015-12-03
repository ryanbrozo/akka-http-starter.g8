package $package$.persistence.mongo

import com.mongodb.casbah.Imports._
import scala.collection.mutable

trait MongoSpecCommon extends TypedMongo {

  def fixture = new {
    val builder = MongoDBObject.newBuilder
  }

  def build[A: MongoDeserializer](b: mutable.Builder[(String, Any), DBObject], args: (String, Any)*): A = {
    args foreach { b += _ }
    deserialize[A](b.result())
  }
}
