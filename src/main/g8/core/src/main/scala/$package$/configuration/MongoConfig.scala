package $package$.configuration

import com.mongodb.casbah.MongoClient


trait MongoConfig {

  this: Config =>

  override protected val keyRoot: String = "mongo"

  lazy val server = conf.getString(key("server"))
  lazy val port = conf.getInt(key("port"))
  lazy val database = conf.getString(key("database"))
  implicit lazy val db = MongoClient(server, port)(database)

}
