package edu.mayo.lcd.rest.controller

import org.scalatra.{NotFound, ScalatraServlet}
import org.scalatra.json._
import scala.slick.session.Database
import org.json4s.{Formats, DefaultFormats}
import edu.mayo.lcd.rest.model.PropertyRepo
import edu.mayo.lcd.rest.model.mysql.PropertyMysqlRepo

class PropertiesController(implicit val database: Database) extends ScalatraServlet with NativeJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  protected val propertyDao: PropertyRepo = new PropertyMysqlRepo

  before() {
    contentType = formats("json")
  }

  get("/") {
    propertyDao.getAll.getOrElse(NotFound)
  }

}
