package $package$.configuration

import $package$.persistence.mongo.MongoDBConnection
import com.typesafe.config.ConfigFactory

trait Config {

  protected val keyRoot: String

  lazy protected val conf = ConfigFactory.load()

  def key(name: String) = s"\$keyRoot.\$name"

}

trait Configured extends Config with MongoConfig {

  import com.softwaremill.macwire._

  lazy val mongoDatabaseConnection = wire[MongoDBConnection]
}