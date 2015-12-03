package $package$.core

import akka.actor._
import $package$.core.actors._
import $package$.persistence.mongo.MongoDataMappers.MongoTestDataMapper

trait CoreActors extends CoreActorRefs {
  this: Core =>

  override lazy val test: ActorRef = system.actorOf(Props(classOf[TestActor], new MongoTestDataMapper("test")), "TestActor")

}