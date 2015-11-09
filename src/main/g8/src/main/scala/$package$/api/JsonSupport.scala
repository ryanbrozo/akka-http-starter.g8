package $package$.api

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{DefaultFormats, native}

/**
 * JsonSupport.scala
 *
 */
trait JsonSupport extends Json4sSupport {

  implicit val serialization = native.Serialization
  implicit val formats = DefaultFormats
}
