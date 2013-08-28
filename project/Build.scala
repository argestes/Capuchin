import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "Capuchin"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.h2database" % "h2" % "1.3.173",
    "com.typesafe.play" %% "play-slick" % "0.5.0.2-SNAPSHOT",
    "com.typesafe.slick" %% "slick" % "1.0.1",
    jdbc,
    anorm,
    cache
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    routesImport += "models._"
  )

}
