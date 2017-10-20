import sbt._
import Keys._

object WebBundleBuild extends Build {
  override lazy val settings = super.settings ++ XitrumPackage.skip

  lazy val core = Project(
    id = "core",
    base = file("core"),


    settings = Xitrum.settings ++ Xitrum.templateSettings ++ Core.databaseSettings ++ Seq(
      name := "cityhelper-core",

      resolvers ++= Seq("RoundEights" at "http://maven.spikemark.net/roundeights"),

      libraryDependencies += "com.ning" % "async-http-client" % "1.8.8",

      libraryDependencies += "com.google.code.simple-spring-memcached" % "spymemcached" % "2.8.4",

      libraryDependencies += "com.roundeights" %% "hasher" % "1.0.0"
    ) ++ XitrumPackage.skip
  )

  lazy val ascendanceWeb = Project(
    id = "web",
    base = file("web"),
    settings =
      Xitrum.settings ++
      Xitrum.templateSettings ++
      Seq(name := "cityhelper-web") ++
      XitrumPackage.copy("script", "config", "public", "script")
  ).dependsOn(core)

  lazy val ascendanceAdmin = Project(
    id = "admin",
    base = file("admin"),
    settings =
      Xitrum.settings ++
        Xitrum.templateSettings ++
        Seq(name := "admin") ++
        XitrumPackage.copy("script", "config", "public", "script")
  ).dependsOn(core)
}
