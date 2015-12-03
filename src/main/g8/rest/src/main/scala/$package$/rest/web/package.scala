package $package$.rest

import akka.stream.Materializer
import $package$.core._
import $package$.rest.api.Api
import $package$.rest.core._

package object web {

  trait Web {
    this: Api with Core with RestActorRefs =>

    implicit val materializer: Materializer

  }

}
