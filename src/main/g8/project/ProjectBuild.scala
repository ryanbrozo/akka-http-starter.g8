import sbt.Keys._
import sbt._

object BuildDependencies {
  private val AKKA_VERSION = "2.3.12"
  private val AKKA_STREAMS_VERSION = "2.0-M1"
  private val JSON4S_VERSION = "3.3.0.RC3"
  private val MACWIRE_VERSION = "2.1.0"

  // Dependencies
  val akkaStreams =       "com.typesafe.akka"             %% "akka-stream-experimental"                  % AKKA_STREAMS_VERSION
  val akkaHttpCore =      "com.typesafe.akka"             %% "akka-http-core-experimental"               % AKKA_STREAMS_VERSION
  val akkaHttp =          "com.typesafe.akka"             %% "akka-http-experimental"                    % AKKA_STREAMS_VERSION
  val akkaHttpTestKit =   "com.typesafe.akka"             %% "akka-http-testkit-experimental"            % AKKA_STREAMS_VERSION   % "test"
  val akkaHttpSprayJson = "com.typesafe.akka"             %% "akka-http-spray-json-experimental"         % AKKA_STREAMS_VERSION
  val akkaActor =         "com.typesafe.akka"             %% "akka-actor"                                % AKKA_VERSION
  val akkaTestKit =       "com.typesafe.akka"             %% "akka-testkit"                              % AKKA_VERSION           % "test"
  val akkaRemote =        "com.typesafe.akka"             %% "akka-remote"                               % AKKA_VERSION
  val scalaLogging =      "com.typesafe.scala-logging"    %% "scala-logging"                             % "3.1.0"
  val logback =           "ch.qos.logback"                %  "logback-classic"                           % "1.0.13"
  val specs2 =            "org.specs2"                    %% "specs2-core"                               % "2.4.13"               % "test"
  val scalaTest =         "org.scalatest"                 %% "scalatest"                                 % "2.2.4"                % "test"
  val json4sCore =        "org.json4s"                    %% "json4s-core"                               % JSON4S_VERSION
  val json4sNative =      "org.json4s"                    %% "json4s-native"                             % JSON4S_VERSION
  val akkaHttpJson4s =    "de.heikoseeberger"             %% "akka-http-json4s"                          % "1.2.0"
  val nScalaTime =        "com.github.nscala-time"        %% "nscala-time"                               % "2.4.0"
  val commonsCodec =      "commons-codec"                 %  "commons-codec"                             % "1.7"
  val eaioUuid =          "com.eaio.uuid"                 %  "uuid"                                      % "3.2"
  val commonsDaemon =     "commons-daemon"                %  "commons-daemon"                            % "1.0.15"
  val casbah =            "org.mongodb"                   %% "casbah"                                    % "3.0.0"
  val macwireMacros =     "com.softwaremill.macwire"      %% "macros"                                    % MACWIRE_VERSION        % "provided"
  val macwireUtil =       "com.softwaremill.macwire"      %% "util"                                      % MACWIRE_VERSION
  val macwireProxy =      "com.softwaremill.macwire"      %% "proxy"                                     % MACWIRE_VERSION
  val macwire =           macwireMacros :: macwireUtil :: macwireProxy :: Nil

  // Resolvers
  val hseebergerResolver = "hseeberger at bintray" at "http://dl.bintray.com/hseeberger/maven"
}

object BuildSettings {
  import BuildDependencies._

  val SCALA_VERSION = "2.11.7"
  val APP_VERSION = "0.1"

  lazy val commonSettings = Seq(
    organization        := "com.bengga",
    scalaVersion        := SCALA_VERSION,
    version             := APP_VERSION
  )

  lazy val coreSettings = Seq(
    resolvers ++= Seq(
      hseebergerResolver
    ),
    libraryDependencies ++= Seq(
      akkaActor,
      akkaTestKit,
      scalaLogging,
      nScalaTime,
      eaioUuid,
      casbah,
      logback,
      commonsCodec,
      scalaTest
    ) ++ macwire,
    autoAPIMappings := true,
    scalacOptions in Test ++= Seq("-Yrangepos", "-deprecation")
  )

  lazy val restSettings = Seq(
    resolvers ++= Seq(
      hseebergerResolver
    ),
    libraryDependencies ++= Seq(
      akkaActor,
      akkaRemote,
      akkaStreams,
      akkaHttpCore,
      akkaHttp,
      akkaHttpTestKit,
      akkaHttpJson4s,
      json4sCore,
      json4sNative,
      scalaLogging,
      nScalaTime,
      eaioUuid,
      logback,
      commonsCodec,
      scalaTest
    ),
    autoAPIMappings := true,
    scalacOptions in Test ++= Seq("-Yrangepos", "-deprecation")
  )

  lazy val serviceSettings = Seq(
    libraryDependencies ++= Seq(
      akkaActor,
      akkaRemote,
      scalaLogging,
      nScalaTime,
      logback,
      commonsDaemon,
      scalaTest
    ),
    autoAPIMappings := true,
    scalacOptions in Test ++= Seq("-Yrangepos", "-deprecation")
  )
}

object ProjectBuild extends Build {

  import BuildSettings._

  lazy val main = Project(
    id = "bengga-analytics",
    base = file(".")
  )
    .aggregate(core, rest)
    .settings(commonSettings: _*)

  lazy val core = Project(
    id = "bengga-analytics-core",
    base = file("core")
  )
    .settings(commonSettings: _*)
    .settings(coreSettings: _*)

  lazy val rest = Project(
    id = "bengga-analytics-rest",
    base = file("rest")
  )
    .dependsOn(core % "compile->compile;test->test")
    .settings(commonSettings: _*)
    .settings(restSettings: _*)

  lazy val service = Project(
    id = "bengga-analytics-service",
    base = file("service")
  )
    .dependsOn(core)
    .settings(commonSettings: _*)
    .settings(serviceSettings: _*)
}