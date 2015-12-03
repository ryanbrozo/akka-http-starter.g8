package $package$
package core

import akka.actor._
import akka.testkit._
import $package$.core.actors._
import $package$.persistence.dummy._
import org.joda.time.DateTime
import org.scalatest._

class TestActorSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with FlatSpecLike
  with Matchers
  with BeforeAndAfterAll {

  import TestActor._
  import DummyTestMapper._
  import util.Imports._

  def this() = this(ActorSystem("test"))

  val actor = system.actorOf(Props(classOf[TestActor], new DummyTestMapper))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "TestActor" should "get all top items" in {

    actor ! GetTopFollowers(0, 6)

    expectMsg(topItems)
  }

  it should "get a test item" in {

    actor ! Get(testData2.id)

    expectMsg(testData2)
  }

  it should "get a NoSuchItem message" in {

    actor ! Get(generateId)
  }

}
