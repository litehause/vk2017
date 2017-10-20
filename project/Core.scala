import sbt._
import sbt.Keys._

object Core {

  val elastic4sVersion = "5.4.2"

  lazy val settings = Project.defaultSettings ++ Seq(
    organization := "ru.cityhelper",

    version      := "1.0-SNAPSHOT",

    scalaVersion := "2.11.8",

    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),

    libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2",

    libraryDependencies += "com.google.code.simple-spring-memcached" % "spymemcached" % "2.8.4",

    libraryDependencies += "org.imgscalr" % "imgscalr-lib" % "4.2"//,

//    libraryDependencies ++= Seq(
//      "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
      // for the tcp client
//      "com.sksamuel.elastic4s" %% "elastic4s-tcp" % elastic4sVersion,

      // for the http client
//      "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,

      // if you want to use reactive streams
//      "com.sksamuel.elastic4s" %% "elastic4s-streams" % elastic4sVersion,

      // testing
//      "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test",
//      "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sVersion
//    ),

//    libraryDependencies += "joda-time" % "joda-time" % "2.9.9",
// libraryDependencies += "com.carrotsearch" % "hppc" % "0.7.2",
//    libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.3",
//    libraryDependencies += "com.tdunning" % "t-digest" % "3.1"
  )

  lazy val databaseSettings = Seq(
    libraryDependencies += "com.typesafe.slick" % "slick_2.11" % "2.1.0",

    libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",

    libraryDependencies += "com.zaxxer" % "HikariCP" % "2.3.2"
  )  
  
}
