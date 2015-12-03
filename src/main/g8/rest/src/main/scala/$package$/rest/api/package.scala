package $package$.rest

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Rejection
import $package$.core._
import $package$.rest.api.services._
import $package$.rest.core._
import $package$.rest.util.serialization.JsonSupport

import scala.concurrent.ExecutionContextExecutor

package object api {

  trait Api extends JsonSupport {

    this: Core with RestActorRefs =>

    implicit val executor: ExecutionContextExecutor

    import Directives._

    val routes =
      logHeaders { ip =>
        encodeResponse {
          new TestService(test).route
        }
      }
  }

  case class AppRejection(error: AppError) extends Rejection

}
