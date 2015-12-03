package $package$.rest.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import $package$.core.Core
import $package$.rest.api.{Api, Implicits}
import $package$.rest.core.RestActors
import $package$.rest.util.serialization.JsonSupport


trait BootedWeb extends Web with Api with RestActors with Core with JsonSupport {

  import Implicits._

  override implicit lazy val system = ActorSystem($name$)

  override implicit lazy val executor = system.dispatcher

  override implicit lazy val materializer: Materializer = ActorMaterializer()

  Http().bindAndHandle(routes, "localhost", 8080)

  sys.addShutdownHook(system.shutdown())

}