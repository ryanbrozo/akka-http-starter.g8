package $package$.rest.util.serialization

import java.util.UUID

import org.joda.time.DateTime
import org.scalatest._
import org.json4s._
import org.json4s.native.Serialization.{write,read}

class Json4sSerializersSpec extends FlatSpec with Matchers {

  import $package$.util.Imports._

  implicit val serialization = native.Serialization
  implicit val formats = DefaultFormats ++ Json4sSerializers.formats

  case class UUIDTest(test: UUID)
  case class DateTimeTest(test: DateTime)

  "Json4sSerializers" should "be able to serialize a java.util.UUID to a URL-safe Base64" in {
    val value = generateId
    val testClass = UUIDTest(value)

    write(testClass) should be(
      s"""{"test":"${value.toUrlSafeBase64}"}"""
    )
  }

  it should "be able to serialize a Joda DateTime to an ISO8601 string" in {
    val value = DateTime.now()
    val testClass = DateTimeTest(value)

    write(testClass) should be(
      s"""{"test":"${value.toISO8601String}"}"""
    )
  }
}
