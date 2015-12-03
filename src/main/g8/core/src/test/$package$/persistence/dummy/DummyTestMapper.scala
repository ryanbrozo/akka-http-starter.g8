package $package$.persistence.dummy

import java.util.UUID

import $package$.domain.models._
import $package$.persistence.TestDataMapper
import $package$.util.Imports._
import org.joda.time.DateTime

object DummyTestMapper {

  val testData1 = Test(
    generateId,
    "Test 1"
  )

  val testData2 = Test(
    generateId,
    "Test 2"
  )

  val testData3 = Test(
    generateId,
    "Test 3"
  )

  val topItems = List(testData1, testData2, testData3)
}

class DummyTestMapper extends TestDataMapper {

  import DummyTestMapper._

  /**
    * Retrieves the top test items
    *
    * @param start Start index
    * @param limit Number of items to return
    * @return Iterable sequence of Followers
    */
  override def getTop(start: Int, limit: Int): Iterable[Test] = topItems.slice(start, start + limit).toIterable

  /**
    * Updates an existing model from the database. If one is not existing, this method
    * inserts it. Uses the models Id as key
    *
    * @param value Model instance to be updated
    */
  override def update(value: Test): Unit = {}

  /**
    * Inserts a Model object into the database
    *
    * @param value Model instance to be inserted
    */
  override def insert(value: Test): Unit = {}

  /**
    * Finds a model instance given an Id
    *
    * @param id id of model to be searched
    *
    * @return Model instance wrapped as a Future[Option]. Future.successful(Some(x)) if found,
    *         Future.successful(None) if not
    */
  override def findOne(id: UUID) = {
    if (id == testData1.id)
      Some(testData1)
    else if (id == testData2.id)
      Some(testData2)
    else if (id == testData3.id)
      Some(testData3)
    else None
  }

  /**
    * Deletes an existing model from the database
    *
    * @param value Model instance to be deleted
    */
  override def delete(value: Test): Unit = {}

  /**
    * Retrieves a Test given its name
    *
    * @param name Name to be searched
    * @return [[Test]] instance wrapped as an Option
    */
  override def findOne(name: String): Option[Test] = {
    if (name == testData1.name)
      Some(testData1)
    else if (name == testData2.name)
      Some(testData2)
    else if (name == testData3.name)
      Some(testData3)
    else None
  }
}
