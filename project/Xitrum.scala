import sbt._
import Keys._

import org.fusesource.scalate.ScalatePlugin._
import ScalateKeys._

object Xitrum {
  
  lazy val settings = Core.settings ++ Seq(

    scalaVersion := "2.11.8",

    libraryDependencies += "tv.cntt" %% "xitrum" % "3.28.4",

    // Xitrum uses SLF4J, an implementation of SLF4J is needed
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",

    // For writing condition in logback.xml
    libraryDependencies += "org.codehaus.janino" % "janino" % "3.0.7",

    libraryDependencies += "tv.cntt" %% "xitrum-scalate" % "2.8.0",

    // xgettext i18n translation key string extractor is a compiler plugin ---------

    autoCompilerPlugins := true,

    //addCompilerPlugin("tv.cntt" %% "xgettext" % "1.5.1"),

    //scalacOptions += "-P:xgettext:xitrum.I18n",


    // Put config directory in classpath for easier development --------------------

    // For "sbt console"
    unmanagedClasspath in Compile <+= (baseDirectory) map { bd => Attributed.blank(bd / "config") },

    // For "sbt run"
    unmanagedClasspath in Runtime <+= (baseDirectory) map { bd => Attributed.blank(bd / "config") }
  )  

  lazy val templateSettings = scalateSettings ++ Seq(
    // Precompile Scalate templates
    ScalateKeys.scalateTemplateConfig in Compile := Seq(TemplateConfig(
      baseDirectory.value / "src" / "main" / "scalate",
      Seq.empty,
      Seq(Binding("helper", "xitrum.Action", true))
    ))
  )  

}
