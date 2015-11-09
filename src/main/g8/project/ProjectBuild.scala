import sbt._
import Keys._

object BuildDependencies {
  private val AKKA_VERSION = "2.3.12"
  private val AKKA_STREAMS_VERSION = "2.0-M1"
  private val JSON4S_VERSION = "3.3.0.RC3"

  // Dependencies
  val akkaStreams =       "com.typesafe.akka"             %% "akka-stream-experimental"                  % AKKA_STREAMS_VERSION
  val akkaHttpCore =      "com.typesafe.akka"             %% "akka-http-core-experimental"               % AKKA_STREAMS_VERSION
  val akkaHttp =          "com.typesafe.akka"             %% "akka-http-experimental"                    % AKKA_STREAMS_VERSION
  val akkaHttpTestKit =   "com.typesafe.akka"             %% "akka-http-testkit-experimental"            % AKKA_STREAMS_VERSION   % "test"
  val akkaHttpSprayJson = "com.typesafe.akka"             %% "akka-http-spray-json-experimental"         % AKKA_STREAMS_VERSION
  val akkaActor =         "com.typesafe.akka"             %% "akka-actor"                                % AKKA_VERSION
  val scalaLogging =      "com.typesafe.scala-logging"    %% "scala-logging"                             % "3.1.0"
  val logback =           "ch.qos.logback"                %  "logback-classic"                           % "1.0.13"
  val specs2 =            "org.specs2"                    %% "specs2-core"                               % "2.4.13"               % "test"
  val scalaTest =         "org.scalatest"                 %% "scalatest"                                 % "2.2.4"                % "test"
  val json4sCore =        "org.json4s"                    %% "json4s-core"                               % JSON4S_VERSION
  val json4sNative =      "org.json4s"                    %% "json4s-native"                             % JSON4S_VERSION
  val akkaHttpJson4s =    "de.heikoseeberger"             %% "akka-http-json4s"                          % "1.2.0"


  // Resolvers
  val hseebergerResolver = "hseeberger at bintray" at "http://dl.bintray.com/hseeberger/maven"
}

object BuildSettings {
  import BuildDependencies._

  val SCALA_VERSION = "2.11.7"
  val APP_VERSION = "0.1"

  lazy val commonSettings = Seq(
    organization        := "$package$",
    scalaVersion        := SCALA_VERSION,
    version             := APP_VERSION
  )

  lazy val restSettings = Seq(
    resolvers ++= Seq(
      hseebergerResolver
    ),
    libraryDependencies ++= Seq(
      akkaActor,
      akkaStreams,
      akkaHttpCore,
      akkaHttp,
      akkaHttpTestKit,
      akkaHttpJson4s,
      json4sCore,
      json4sNative,
      scalaLogging,
      logback,
      scalaTest
    ),
    autoAPIMappings := true,
    scalacOptions in Test ++= Seq("-Yrangepos", "-deprecation")
  )
}

object ProjectBuild extends Build {
  import BuildSettings._

  lazy val main = Project(
    id = "$name$",
    base = file(".")
  )
    .settings(commonSettings: _*)
    .settings(restSettings: _*)
}