package $package$.rest.core

import akka.actor._
import $package$.core._

trait RestActors extends CoreActors with RestActorRefs {

  this: Core =>

//  override lazy val eventRemote: ActorSelection = system.actorSelection("akka.tcp://bengga-analytics-service@127.0.0.1:2552/user/EventActor")
}