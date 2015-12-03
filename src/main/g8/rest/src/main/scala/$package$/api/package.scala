package $package$

import akka.http.scaladsl.server.Rejection
import $package$.api.services.PersonService
import $package$.core.{Core, CoreActors, AppError}

/**
 * package.scala
 *
 */
package object api {

  trait Api extends CoreActors {

    this: Core =>

    val routes = new PersonService(person).route

  }

  case class AppRejection(error: AppError) extends Rejection


}
