package $package$

import akka.actor.{Props, ActorSystem}
import akka.http.scaladsl.model.StatusCode
import $package$.core.actors.PersonActor

import scala.concurrent.ExecutionContextExecutor

package object core {

  abstract class AppError(val code: StatusCode, val message: String)

  case class AppException(errorMessage: String)

  trait Core {

    implicit val system: ActorSystem

    implicit val executor: ExecutionContextExecutor

  }

  trait CoreActors {
    this: Core =>

    val person = system.actorOf(Props[PersonActor])

  }

}
