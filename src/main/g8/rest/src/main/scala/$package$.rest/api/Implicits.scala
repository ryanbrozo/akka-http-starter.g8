package $package$.rest.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler}
import $package$.rest.core._
import $package$.rest.util.serialization.JsonSupport

object Implicits extends JsonSupport {

  implicit def rejectionHandler = RejectionHandler.newBuilder()
    .handle {
      case AppRejection(error) => complete(error.code, AppException(error.message))
    }
    .result()

  implicit def exceptionHandler = ExceptionHandler {
    case e: Throwable => complete(StatusCodes.InternalServerError, AppException(e.getMessage))
  }
}

