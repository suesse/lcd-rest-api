package edu.mayo.lcd.rest.controller

import org.scalatra.swagger.{SwaggerSupport, Swagger}
import org.scalatra.ScalatraServlet
import org.scalatra.json.NativeJsonSupport
import org.json4s.{DefaultFormats, Formats}
import edu.mayo.lcd.rest.model.{Keyword}

class KeywordsController() extends ScalatraServlet with NativeJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

//  val getKeywords = (apiOperation[List[Keyword]]("getKeywords")
//    summary "Get all keywords."
//    notes "Returns a list of all the keywords.")

  get("/") {
    Keyword.getAllKeywords
  }

//  val getKeyword = (apiOperation[Keyword]("getKeyword")
//    summary "Get keyword by id."
//    notes "Returns a specific keyword."
//    parameter queryParam[Option[String]]("id").description("Id of keyword to retrieve"))

  get("/:id"){
  }

//  val deleteKeyword = (apiOperation[Keyword]("deleteKeyword")
//    summary "Deletes a keyword."
//    notes ""
//    parameter queryParam[Option[String]]("id").description("Id of the keyword to delete."))

  delete("/:id"){
  }


//  val createKeyword = (apiOperation[Keyword]("createKeyword")
//    summary "Create a new keyword."
//    notes "")

  post("/"){
  }

//  val updateKeyword = (apiOperation[Keyword]("updateKeyword")
//    summary "Updates a keyword."
//    notes ""
//    parameter queryParam[Option[String]]("id").description("Id of the keyword to update."))

  put("/:id") {
  }
}
