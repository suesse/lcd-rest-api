package edu.mayo.lcd.rest.controller

import org.scalatra.swagger.{SwaggerSupport, Swagger}
import org.scalatra.ScalatraServlet
import org.scalatra.json.NativeJsonSupport
import org.json4s.{DefaultFormats, Formats}
import edu.mayo.lcd.rest.model.{Keyword}

class KeywordsController(implicit val swagger: Swagger) extends ScalatraServlet with NativeJsonSupport with SwaggerSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  // The name of our application. This will show up in the Swagger docs.
  override protected val applicationName = Some("keywords")

  // A description of our application. This will show up in the Swagger docs.
  protected val applicationDescription = "The keywords API. It exposes operations for browsing, searching, updating and deleting of keywords."

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  val getKeywords = (apiOperation[List[Keyword]]("getKeywords")
    summary "Get all keywords."
    notes "Returns a list of all the keywords.")

  get("/", operation(getKeywords)) {
//    Keyword.getAllKeywords
  }

  val getKeyword = (apiOperation[Keyword]("getKeyword")
    summary "Get keyword by id."
    notes "Returns a specific keyword."
    parameter queryParam[Option[String]]("id").description("Id of keyword to retrieve"))

  get("/:id", operation(getKeyword)){
  }

  val deleteKeyword = (apiOperation[Keyword]("deleteKeyword")
    summary "Deletes a keyword."
    notes ""
    parameter queryParam[Option[String]]("id").description("Id of the keyword to delete."))

  delete("/:id", operation(deleteKeyword)){
  }


  val createKeyword = (apiOperation[Keyword]("createKeyword")
    summary "Create a new keyword."
    notes "")

  post("/", operation(createKeyword)){
  }

  val updateKeyword = (apiOperation[Keyword]("updateKeyword")
    summary "Updates a keyword."
    notes ""
    parameter queryParam[Option[String]]("id").description("Id of the keyword to update."))

  put("/:id", operation(updateKeyword)) {
  }
}
