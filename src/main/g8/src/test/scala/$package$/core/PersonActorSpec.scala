package $package$.core

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import $package$.core.actors.PersonActor
import $package$.domain.Person
import org.scalatest._

/**
 * PersonActorSpec.scala
 *
 */
class PersonActorSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with FlatSpecLike
  with Matchers
  with BeforeAndAfterAll {

  import PersonActor._

  def this() = this(ActorSystem("test"))

  val bob = Person("1", "Bob", 23)
  val alice = Person("2", "Alice", 50)

  val persons = List(bob, alice)

  val actor = system.actorOf(Props[PersonActor])

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "PersonActor" should "get all people" in {

    actor ! GetAll

    expectMsg(persons)
  }

  it should "get a person given its id" in {
    actor ! Get("2")

    expectMsg(alice)
  }

  it should "get a NoSuchPerson message when no such id exists" in {
    actor ! Get("4")

    expectMsg(NoSuchPerson)
  }

}
