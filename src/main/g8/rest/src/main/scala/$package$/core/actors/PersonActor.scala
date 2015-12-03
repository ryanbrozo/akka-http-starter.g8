package $package$.core.actors

import akka.actor.Actor
import akka.http.scaladsl.model.StatusCodes
import $package$.core.AppError
import $package$.domain.Person

object PersonActor {

  case object GetAll

  case class Get(id: String)

  case object NoSuchPerson extends AppError(StatusCodes.NotFound, "No such person")

}

class PersonActor extends Actor {

  import PersonActor._

  val bob = Person("1", "Bob", 23)
  val alice = Person("2", "Alice", 50)

  val persons = Map[String, Person](
    bob.id -> bob,
    alice.id -> alice
  )

  override def receive: Receive = {
    case GetAll =>
      sender ! persons.map { case (_, p) => p} .toList

    case Get(id) =>
      if (persons.keys.exists(_ == id))
        sender ! persons(id)
      else
        sender ! NoSuchPerson
  }
}
