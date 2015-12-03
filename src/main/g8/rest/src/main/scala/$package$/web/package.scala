package $package$

import akka.stream.Materializer
import $package$.api.Api
import $package$.core.Core

package object web {

  trait Web {
    this: Api with Core =>

    implicit val materializer: Materializer

  }

}
