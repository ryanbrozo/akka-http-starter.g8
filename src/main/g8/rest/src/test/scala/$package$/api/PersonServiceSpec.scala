package $package$.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import $package$.core.actors.PersonActor
import PersonActor.NoSuchPerson
import $package$.core.{AppException, Core}
import $package$.domain.Person
import org.scalatest._

/**
 * PersonServiceSpec.scala
 *
 */
class PersonServiceSpec
  extends FlatSpec
  with Matchers
  with ScalatestRouteTest
  with Api
  with Core {

  import Implicits._

  override implicit val executor = system.dispatcher

  val bob = Person("1", "Bob", 23)
  val alice = Person("2", "Alice", 50)
  val noSuchPersonException = AppException(NoSuchPerson.message)
  val testException = AppException("test")

  val persons = List(bob, alice)

  "PersonService" should "return all persons in /person/all" in {
    Get("/person/all") ~> routes ~> check {
      responseAs[List[Person]] should be(persons)
    }
  }
  it should "return alice in /person/2" in {
    Get("/person/2") ~> routes ~> check {
      responseAs[Person] should be(alice)
    }
  }

  it should "return 404 in /person/4" in {
    Get("/person/4") ~> Route.seal(routes) ~> check {
      status should be(StatusCodes.NotFound)
      responseAs[AppException] should be(noSuchPersonException)
    }
  }

  it should "reject with NoSuchPerson in /person/4" in {
    Get("/person/4") ~> routes ~> check {
      rejection should be(AppRejection(NoSuchPerson))
    }
  }

  it should "return 500 in /person/error" in {
    Get("/person/error") ~> Route.seal(routes) ~> check {
      status should be(StatusCodes.InternalServerError)
      responseAs[AppException] should be(testException)
    }
  }
}
