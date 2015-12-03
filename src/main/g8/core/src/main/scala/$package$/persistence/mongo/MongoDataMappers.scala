package $package$.persistence.mongo

import java.util.UUID

import $package$.configuration.Configured
import $package$.domain.models._
import $package$.persistence._
import $package$.persistence.mongo.Imports._
import com.mongodb.casbah.Imports._

abstract class MongoDataMapper[T <: Model](implicit serializer: MongoSerializer[T], deserializer: MongoDeserializer[T])
  extends DataMapper[T] with TypedMongo {

  val collection: MongoCollection

  /**
    * Inserts a Model object into the database
    *
    * @param value Model instance to be inserted
    */
  def insert(value: T): Unit = {
    collection.insert(value)
  }

  /**
    * Updates an existing model from the database. If one is not existing, this method
    * inserts it. Uses the models Id as key
    *
    * @param value Model instance to be updated
    */
  def update(value: T): Unit = {
    collection.update(entityIdFilter(value.id), value)
  }

  /**
    * Deletes an existing model from the database
    *
    * @param value Model instance to be deleted
    */
  def delete(value: T): Unit = {
    collection.remove(value)
  }

  /**
    * Finds a model instance given an Id
    *
    * @param id id of model to be searched
    *
    * @return Model instance wrapped as a Future[Option]. Future.successful(Some(x)) if found,
    *         Future.successful(None) if not
    */
  def findOne(id: UUID): Option[T] = {
    collection
      .findOne(entityIdFilter(id))
      .map(mapper[T])
  }
}

object MongoDataMappers extends MongoExpressions {

  class MongoTestDataMapper(collectionName: String)
    extends MongoDataMapper[Test]
    with TestDataMapper
    with Configured {

    override lazy val collection = mongoDatabaseConnection.db(collectionName)

    /**
      * Retrieves the top test items
      *
      * @param start Start index
      * @param limit Number of items to return
      * @return Iterable sequence of Followers
      */
    override def getTop(start: Int, limit:Int): Iterable[Test] = {
      collection
        .find()
        .skip(start)
        .limit(limit)
        .map(mapper[Test])
        .toIterable
    }

    /**
      * Retrieves a Test given its name
      *
      * @param name Name to be searched
      * @return [[Test]] instance wrapped as an Option
      */
    override def findOne(name: String): Option[Test] = {
      collection
        .findOne(nameFilter(name))
        .map(mapper[Test])
    }
  }

}
