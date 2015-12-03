package $package$.persistence

import java.util.UUID

import $package$.domain.models.{Test, Model}

/**
  * DataMapper.scala
  *
  * Created by rye on 11/16/15 2:24 PM.
  */

trait DataMapper[T <: Model] {

  /**
    * Inserts a Model object into the database
    *
    * @param value Model instance to be inserted
    */
  def insert(value: T): Unit

  /**
    * Updates an existing model from the database. If one is not existing, this method
    * inserts it. Uses the models Id as key
    *
    * @param value Model instance to be updated
    */
  def update(value: T): Unit

  /**
    * Deletes an existing model from the database
    *
    * @param value Model instance to be deleted
    */
  def delete(value: T): Unit

  /**
    * Finds a model instance given an Id
    *
    * @param id id of model to be searched
    *
    * @return Model instance wrapped as a Future[Option]. Future.successful(Some(x)) if found,
    *         Future.successful(None) if not
    */
  def findOne(id: UUID): Option[T]

}

trait TestDataMapper extends DataMapper[Test] {

  /* TODO: Put Test specific methods here */

  /**
    * Retrieves the top test items
    *
    * @param start Start index
    * @param limit Number of items to return
    * @return Iterable sequence of Followers
    */
  def getTop(start: Int, limit:Int): Iterable[Test]

  /**
    * Retrieves a Test given its name
    *
    * @param name Name to be searched
    * @return [[Test]] instance wrapped as an Option
    */
  def findOne(name: String): Option[Test]
}



