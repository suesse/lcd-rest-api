package edu.mayo.lcd.rest.controller

import org.scalatra.swagger.{SwaggerSupport, Swagger}

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import edu.mayo.lcd.rest.model.Definition
import org.scalatra.ScalatraServlet
import scala.slick.session.Database

class DefinitionsController(implicit val database: Database) extends ScalatraServlet with NativeJsonSupport {

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/") {
    params.get("q") match {
      case Some(q) => Definition.getAll(database)
      case None => Definition.getAll(database)
    }
  }

  protected implicit def jsonFormats = DefaultFormats
}
