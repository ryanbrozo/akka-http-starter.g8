package $package$.api.services

import akka.actor.ActorRef
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import $package$.api.{AppRejection, JsonSupport}
import $package$.core.actors.PersonActor
import $package$.domain.Person

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * PersonService.scala
 *
 * Example Service
 */
class PersonService(person: ActorRef)(implicit executionContext: ExecutionContext)
  extends JsonSupport {

  import PersonActor._
  import akka.pattern.ask
  implicit val timeout = Timeout(3 seconds)

  val route = get {
    pathPrefix("person") {
      path("all"){
        onSuccess {
          (person ? GetAll).mapTo[List[Person]]
        } { complete(_) }
      } ~
      path("error") {
        throw new Exception("test")
      } ~
      path(Segment) { id =>
        onSuccess {
          person ? Get(id)
        } {
          case p: Person =>
            complete(p)
          case NoSuchPerson =>
            reject(AppRejection(NoSuchPerson))
        }
      }
    }
  }
}
