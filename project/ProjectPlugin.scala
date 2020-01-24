import higherkindness.mu.rpc.idlgen.IdlGenPlugin.autoImport._
import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements

  object autoImport {

    lazy val V = new {
      val log4cats      = "1.0.1"
      val muRPC         = "0.19.0"
      val macroParadise = "2.1.1"
      val console4cats  = "0.8.0-M1"
      val silencer      = "1.4.3"
      val pureconfig    = "0.12.1"
    }
  }

  import autoImport._

  //////////////////////////
  ////      Server      ////
  //////////////////////////

  private lazy val logSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "io.chrisdavenport" %% "log4cats-slf4j" % V.log4cats
    )
  )
  lazy val serverProcessSettings: Seq[Def.Setting[_]] = logSettings ++ Seq(
    libraryDependencies += "io.higherkindness" %% "mu-rpc-channel" % "0.20.1")

  lazy val serverProtocolSettings: Seq[Def.Setting[_]] = Seq(
    idlType := "avro",
    srcGenSerializationType := "AvroWithSchema",
    sourceGenerators in Compile += (srcGen in Compile).taskValue,
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "mu-rpc-channel" % V.muRPC
    )
  )

  lazy val serverAppSettings: Seq[Def.Setting[_]] = logSettings ++ Seq(
    libraryDependencies ++= Seq(
      "io.higherkindness"     %% "mu-rpc-server" % V.muRPC,
      "com.github.pureconfig" %% "pureconfig"    % V.pureconfig)
  )

  //////////////////////////
  ////      Client      ////
  //////////////////////////

  lazy val clientCommonSettings: Seq[Def.Setting[_]] = logSettings

  lazy val clientProcessSettings: Seq[Def.Setting[_]] = logSettings ++ Seq(
    libraryDependencies ++= Seq("io.higherkindness" %% "mu-rpc-netty" % V.muRPC))

  lazy val clientAppSettings: Seq[Def.Setting[_]] = logSettings

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      libraryDependencies ++=
        Seq("com.github.ghik" % "silencer-lib" % V.silencer % Provided cross CrossVersion.full,
          "io.higherkindness" %% "mu-common" % "0.19.0"),
      addCompilerPlugin("org.scalamacros" % "paradise"            % V.macroParadise cross CrossVersion.full),
      addCompilerPlugin("com.github.ghik" % "silencer-plugin"     % V.silencer cross CrossVersion.full),
      addCompilerPlugin("com.olegpy"      %% "better-monadic-for" % "0.3.1"),
      scalaVersion := "2.12.10",
      name := "mu-Hello",
      version := "0.1",
      scalacOptions := Seq(
        "-deprecation",
        "-encoding",
        "UTF-8",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-unchecked",
        "-Xlint",
        "-Yno-adapted-args",
        "-Ywarn-dead-code",
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard",
        "-Xfuture",
        "-Ywarn-unused-import",
        "-Ypartial-unification",
        "-P:silencer:pathFilters=target"
      )
    )

}
