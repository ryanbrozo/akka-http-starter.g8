package $package$.rest
package api.serialization

import java.util.UUID

import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.server.{PathMatcher1, PathMatcher}
import akka.http.scaladsl.server.PathMatcher.{Unmatched, Matched, Matching}
import akka.http.scaladsl.unmarshalling.Unmarshaller
import akka.stream.Materializer
import $package$.util.Imports._
import com.typesafe.scalalogging.StrictLogging
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}
import scala.util.control.NonFatal

object Imports extends StrictLogging with ParamMatchers with SegmentMatchers

trait ParamMatchers {

  implicit val Base64UUIDParam = new Unmarshaller[String, UUID] {

    override def apply(value: String)(implicit ec: ExecutionContext, materializer: Materializer): Future[UUID] = {
      try Future.successful(value.toUUID)
      catch {
        case NonFatal(ex) => Future.failed(ex)
      }
    }
  }

  implicit val ISO8601DateParam = new Unmarshaller[String, DateTime] {

    override def apply(value: String)(implicit ec: ExecutionContext, materializer: Materializer): Future[DateTime] = {
      try Future.successful(value.toDateTime)
      catch {
        case NonFatal(ex) => Future.failed(ex)
      }
    }
  }
}

trait SegmentMatchers {
  this: StrictLogging =>

  object Base64UUID extends PathMatcher1[UUID] {

    override def apply(path: Path): Matching[Tuple1[UUID]] = {
      Try {
        path.toString().toUUID
      } match {
        case Success(v) => Matched(Path.Empty, Tuple1(v))
        case Failure(e) =>
          logger.warn(s"Unable to convert ${path.toString()} to UUID")
          Unmatched
      }
    }

  }

}
