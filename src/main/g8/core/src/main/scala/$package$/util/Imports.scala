package $package$.util

import java.util.UUID

import org.apache.commons.codec.binary.Base64
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

object Imports extends UuidHelper {

  implicit class RichByteArray(val value: Array[Byte]) {

    /**
      * Converts a byte array into a @see java.util.UUID
      */
    def toUUID = {

      if (value.length != 16) {
        throw new IllegalArgumentException("Length of array must be exactly 16")
      }

      var msb = 0l
      var lsb = 0l

      for (i <- 0 to 7) {
        msb = (msb << 8) | (value(i) & 0xff)
      }
      for (i <- 8 to 15) {
        lsb = (lsb << 8) | (value(i) & 0xff)
      }
      new UUID(msb, lsb)

    }
  }


  implicit class RichString(val value: String) {

    /**
      * Converts a string to a Joda DateTime value
      *
      * @return Joda @see DateTime instance. Input string must be in ISO8601 format
      */
    def toDateTime = new DateTime(value)

    /**
      * Converts a string to a UUID
      *
      * @return @see UUID instance. Input string must be in Base64 format (URL safe or not).
      * @throws IllegalArgumentException Thrown when source string cannot be converted to a UUID
      */
    def toUUID = Base64.decodeBase64(value).toUUID

  }

  implicit class RichUUID(val value: UUID){

    /**
      * Converts a @see java.util.UUID into a byte array
      *
      */
    def toByteArray = {
      val msb = value.getMostSignificantBits
      val lsb = value.getLeastSignificantBits
      val buffer = new Array[Byte](16)

      for (i <- 0 to 7) {
        buffer(i) = (msb >>> 8 * (7 - i)).asInstanceOf[Byte]
      }
      for (i <- 8 to 15) {
        buffer(i) = (lsb >>> 8 * (7 - i)).asInstanceOf[Byte]
      }
      buffer
    }

    /**
      * Encodes a @see java.util.UUID into base64. The result
      * of this method is URL-safe
      */
    def toUrlSafeBase64 = {
      Base64.encodeBase64URLSafeString(value.toByteArray)
    }

    /**
      * Encodes a @see java.util.UUID into base64. The result
      * of this method is not URL-safe
      */
    def toBase64 = {
      Base64.encodeBase64String(value.toByteArray)
    }
  }

  implicit class RichJodaTime(val value: DateTime) {

    /**
      * Converts the source Joda @see DateTime to a date string
      *
      * @return ISO8601 representation of the source @DateTime instance
      */
    def toISO8601String = {
      val fmt = ISODateTimeFormat.dateTime()
      fmt.print(value)
    }
  }

}
