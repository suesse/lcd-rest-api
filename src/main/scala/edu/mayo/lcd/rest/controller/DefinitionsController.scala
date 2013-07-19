package edu.mayo.lcd.rest.controller

import org.json4s.{Formats, DefaultFormats}
import org.scalatra.json._
import edu.mayo.lcd.rest.model.DefinitionRepo
import org.scalatra.{NotFound, ScalatraServlet}
import scala.slick.session.Database
import edu.mayo.lcd.rest.model.mysql.DefinitionMysqlRepo

class DefinitionsController(implicit val database: Database) extends ScalatraServlet with NativeJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val definitionDao: DefinitionRepo = new DefinitionMysqlRepo

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/") {
    params.get("q") match {
      case Some(q) => definitionDao.getAll.getOrElse(NotFound)
      case None => definitionDao.getAll.getOrElse(NotFound)
    }
  }

}
