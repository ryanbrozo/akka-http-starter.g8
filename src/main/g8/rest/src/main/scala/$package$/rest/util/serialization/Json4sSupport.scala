package $package$.rest.util.serialization

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{DefaultFormats, native}


trait JsonSupport extends Json4sSupport {

  implicit val serialization = native.Serialization
  implicit val formats = DefaultFormats ++ Json4sSerializers.formats
}
