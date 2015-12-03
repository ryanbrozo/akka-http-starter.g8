package $package$.persistence

import com.mongodb.casbah.Imports._

package object mongo {

  type MongoDeserializer[A] = DBObject => A

  type MongoSerializer[A] = A => DBObject

  class MongoDBConnection(db: MongoDB) extends DBConnection[MongoDB](db)

}
