package $package$.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import $package$.api.{Implicits, Api, JsonSupport}
import $package$.core.Core


/**
  * BootedWeb.scala
  *
  */
trait BootedWeb extends Web with Api with Core with JsonSupport {

  import Implicits._

  override implicit lazy val system = ActorSystem("$name$")

  override implicit lazy val executor = system.dispatcher

  override implicit lazy val materializer: Materializer = ActorMaterializer()

  Http().bindAndHandle(routes, "localhost", 8080)

  sys.addShutdownHook(system.shutdown())

}
