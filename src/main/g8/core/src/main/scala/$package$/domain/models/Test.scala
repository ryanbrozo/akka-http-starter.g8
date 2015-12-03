package $package$.domain.models

import java.util.UUID

import org.joda.time._


case class Test(id: UUID,
                     name: String) extends Model {
  require(!name.trim.isEmpty, "name should not be empty")
}