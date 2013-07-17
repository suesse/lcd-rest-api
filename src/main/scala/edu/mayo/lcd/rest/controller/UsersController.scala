package edu.mayo.lcd.rest.controller

import org.scalatra.swagger.{SwaggerSupport, Swagger}

import org.json4s.{Formats, DefaultFormats}
import org.scalatra.json._
import edu.mayo.lcd.rest.model.{User}
import org.scalatra.ScalatraServlet
import scala.slick.session.Database

class UsersController(implicit val swagger: Swagger, implicit val database: Database) extends ScalatraServlet with NativeJsonSupport with SwaggerSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  // The name of our application. This will show up in the Swagger docs.
  override protected val applicationName = Some("users")

  // A description of our application. This will show up in the Swagger docs.
  protected val applicationDescription = "The users API. It exposes operations for browsing, searching, updating and deleting of users."

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  val getUsers = (apiOperation[List[User]]("getUsers")
    summary "Get all users."
    notes "Gets a list of all the users, can also filter by role and/or enabled."
    parameter queryParam[Option[String]]("role").description("A role to search for.")
    parameter queryParam[Option[String]]("enabled").description("Retrieved only disabled users."))

  get("/", operation(getUsers)) {
    params.get("enabled") match {
      case Some(en) => User.getAllUsers(params.get("role"), Some(en.equalsIgnoreCase("true")), database)
      case None => User.getAllUsers(params.get("role"), None, database)
    }
  }

  val getUser = (apiOperation[User]("getUser")
    summary "Get user by id."
    notes ""
    parameter queryParam[Option[String]]("id").description("Id of user to retrieve"))

  get("/:id", operation(getUser)){
//    UserData.all find (_.id == params("id")) match {
//      case Some(u) => u
//      case None => halt(404)
//    }
  }

  val deleteUser = (apiOperation[User]("deleteUser")
    summary "Deletes a user."
    notes ""
    parameter queryParam[Option[String]]("id").description("Id of the user to delete."))

  delete("/:id", operation(deleteUser)){

  }


  val createUser = (apiOperation[User]("createUser")
    summary "Create a new user."
    notes "")

  post("/", operation(createUser)){

  }

  val updateUser = (apiOperation[User]("updateUser")
    summary "Updates a user."
    notes ""
    parameter queryParam[Option[String]]("id").description("Id of the user to update."))

  put("/:id", operation(updateUser)) {

  }
}

