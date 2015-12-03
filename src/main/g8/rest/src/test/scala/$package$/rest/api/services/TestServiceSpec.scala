package $package$.rest.api
package services

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import $package$.core._
import $package$.rest.api.models._
import $package$.rest.api._
import $package$.rest.common.TestCommon
import $package$.rest.core._
import org.scalatest._

class TestServiceSpec
  extends FlatSpec
  with Matchers
  with ScalatestRouteTest
  with Api
  with DummyActors
  with Core
  with TestCommon {

  import Implicits._
  import $package$.persistence.dummy.DummyTestMapper._
  import $package$.util.Imports._

  override implicit val executor = system.dispatcher

  "TestService" should "return a list of top items for /test/top" in {
    Get(s"/test/top/0/6") ~> routes ~> check {
      responseAs[List[TestView]] should be(topItemsView)
    }
  }

  it should "return a list of top items honoring the start and limit parameter for /test/top" in {
    Get(s"/test/top/1/2") ~> routes ~> check {
      responseAs[List[TestView]] should be(topItemsViewTail)
    }
  }

  it should "reject with NoSuchItemAppError for an invalid id for /test/<id>" in {
    Get(s"/test/\${generateId.toUrlSafeBase64}") ~> routes ~> check {
      rejection should be(AppRejection(NoSuchItemAppError))
    }
  }

  it should "return a 404 for an invalid id for /test/<id>" in {
    Get(s"/test/\${generateId.toUrlSafeBase64}") ~> Route.seal(routes) ~> check {
      status should be(StatusCodes.NotFound)
      responseAs[AppException] should be(noSuchItemException)
    }
  }

}