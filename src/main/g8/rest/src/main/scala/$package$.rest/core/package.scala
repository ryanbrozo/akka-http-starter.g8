package $package$.rest

import akka.actor.ActorSelection
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import $package$.core._

package object core {

  abstract class AppError(val code: StatusCode, val message: String)

  case object NoSuchItemAppError extends AppError(StatusCodes.NotFound, "No such item")

  case class AppException(errorMessage: String)

  trait RestActorRefs extends CoreActorRefs {
    this: Core =>

//    val eventRemote: ActorSelection

  }

}
