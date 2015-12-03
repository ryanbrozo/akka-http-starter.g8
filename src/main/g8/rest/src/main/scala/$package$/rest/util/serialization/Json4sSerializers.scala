package $package$.rest.util.serialization

import java.util.UUID

import $package$.util.Imports._
import org.joda.time.DateTime
import org.json4s.{MappingException, CustomSerializer}
import org.json4s.JsonAST.JString

object Json4sSerializers {

  val formats = List(ISO8601DateTimeSerializer, UUIDSerializer)

  object ISO8601DateTimeSerializer extends CustomSerializer[DateTime](format => (
    {
      case JString(s) =>
        try {
          s.toDateTime
        } catch {
          case e: Exception => throw new MappingException(s"Unable to parse \$s as a Joda DateTime value", e)
        }
    },
    { case s: DateTime => JString(s.toISO8601String) }
    )
  )

  object UUIDSerializer extends CustomSerializer[UUID](format => (
    { case JString(s) => s.toUUID },
    { case s: UUID => JString(s.toUrlSafeBase64) }
    )
  )

}
