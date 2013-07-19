package edu.mayo.lcd.rest.controller

import org.json4s.{Formats, DefaultFormats}
import org.scalatra.json._
import edu.mayo.lcd.rest.model.UserRepo
import org.scalatra.{NotFound, ScalatraServlet}
import scala.slick.session.Database
import edu.mayo.lcd.rest.model.mysql.UserMysqlRepo

class UsersController(implicit val database: Database) extends ScalatraServlet with NativeJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val userDao: UserRepo = new UserMysqlRepo

  before() {
    contentType = formats("json")
  }

  get("/") {
    params.get("enabled") match {
      case Some(en) => userDao.getAll(params.get("role"), Some(en.equalsIgnoreCase("true")), database).getOrElse(NotFound)
      case None => userDao.getAll(params.get("role"), None, database).getOrElse(NotFound)
    }
  }

  get("/:id") {
    //    UserData.all find (_.id == params("id")) match {
    //      case Some(u) => u
    //      case None => halt(404)
    //    }
  }

  delete("/:id") {

  }

  post("/") {

  }

  put("/:id") {

  }
}

