import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "recznik"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    jdbc,
    anorm,
    "com.typesafe.slick" %% "slick" % "1.0.0",
    "com.typesafe.play" %% "play-slick" % "0.5.0.8",
    "mysql" % "mysql-connector-java" % "5.1.24",
    "com.typesafe.play" %% "play-cache" % "2.2.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "stylesheets" ** "main.less"),
    requireJs += "main.js",
    requireJsShim += "main.js"
  )

}