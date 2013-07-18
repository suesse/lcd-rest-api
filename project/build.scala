import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object LcdRestApiBuild extends Build {
  val Organization = "edu.mayo"
  val Name = "LCD REST API"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.2"
  val ScalatraVersion = "2.2.1"
  val LuceneVersion = "4.3.1"

  lazy val project = Project (
    "lcd-rest-api",
    file("."),
    settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.json4s"   %% "json4s-jackson" % "3.2.4", // for scalatra
        "org.json4s"   %% "json4s-native" % "3.2.4",  // for swagger
        "com.typesafe.slick" %% "slick" % "1.0.1",
        "mysql" % "mysql-connector-java" % "5.1.22",
        "c3p0" % "c3p0" % "0.9.1.2",
        "org.apache.lucene" % "lucene-core" % LuceneVersion,
        "org.apache.lucene" % "lucene-analyzers-common" % LuceneVersion,
        "org.apache.lucene" % "lucene-queryparser" % LuceneVersion,
        "org.apache.lucene" % "lucene-queries" % LuceneVersion,
        "net.liftweb" % "lift-json_2.10" % "2.5.1",
        "org.apache.poi" % "poi" % "3.9",
        "org.apache.poi" % "poi-ooxml" % "3.9",
        "commons-codec" % "commons-codec" % "1.7",
        "commons-logging" % "commons-logging" % "1.1.2",
        "org.apache.httpcomponents" % "httpclient" % "4.2.3",
        "org.apache.httpcomponents" % "httpcore" % "4.2.3",
        "org.apache.httpcomponents" % "httpmime" % "4.2.3",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
