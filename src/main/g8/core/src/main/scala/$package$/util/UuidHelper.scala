package $package$.util

import java.util.UUID
import com.eaio.uuid.{UUID => EaioUUID}


trait UuidHelper {

  def generateId = {
    UUID.fromString((new EaioUUID).toString)
  }

}