package $package$.persistence.mongo

import com.mongodb.casbah.commons.conversions.scala._

object Imports
  extends MongoDeserializers
  with MongoSerializers
  with MongoExpressions {

  JavaUUIDSerializer.register()
  RegisterJodaTimeConversionHelpers()
}
