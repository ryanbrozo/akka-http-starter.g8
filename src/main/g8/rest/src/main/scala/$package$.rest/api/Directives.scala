package $package$.rest.api

import akka.event.Logging
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RouteResult.{Rejected, Complete}
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.LogEntry

object Directives {

  object xForwardedHeaders extends Directive[(String, Option[String], Option[Int])] {
    override def tapply(f: ((String, Option[String], Option[Int])) => Route): Route =
      (extractClientIP.map { _.toOption.map(_.getHostAddress).getOrElse("unknown") }.recoverPF {
        case _ => provide("unk")
      } &
        optionalHeaderValueByName("X-Forwarded-Proto") &
        optionalHeaderValueByName("X-Forwarded-Port").map {
          case Some(port) => Some(port.toInt)
          case None => None
        }
        ) { (ip, proto, port) =>
        f((ip, proto, port))
      }
  }

  object logHeaders extends Directive1[String] {

    override def tapply(f: (Tuple1[String]) => Route): Route = {
      def log(ip: String, protoOption: Option[String], portOption: Option[Int], userAgentOption: Option[String],
              xClientVersionOption: Option[String])(request: HttpRequest): Any => Option[LogEntry] = {
        case a: Complete =>
          val log = s"""$ip${portOption.fold("")(p => s":$p")} ${protoOption.getOrElse("unk")} """ +
            s"""${request.uri} ${a.response.status.intValue} "${userAgentOption.getOrElse("")}" ${xClientVersionOption.getOrElse("")}"""
          Some(LogEntry(log, Logging.InfoLevel))
        case a: Rejected =>
          val log = s"""$ip${portOption.fold("")(p => s":$p")} ${protoOption.getOrElse("unk")} """ +
            s"""${request.uri} $a "${userAgentOption.getOrElse("")}" ${xClientVersionOption.getOrElse("")}"""
          Some(LogEntry(log, Logging.InfoLevel))
        case response=>
          Some(LogEntry("a", Logging.InfoLevel))
      }
      xForwardedHeaders { (ip, protoOption, portOption) =>
        (optionalHeaderValueByName("User-Agent") & optionalHeaderValueByName("X-ClientVersion")) { (userAgentOption, xClientVersionOption) =>
          logRequestResult(log(ip, protoOption, portOption, userAgentOption, xClientVersionOption) _) {
            f(Tuple1(ip))
          }
        }
      }
    }
  }
}
