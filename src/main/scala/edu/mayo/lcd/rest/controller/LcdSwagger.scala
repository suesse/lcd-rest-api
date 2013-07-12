package edu.mayo.lcd.rest.controller

import org.scalatra.swagger.{NativeSwaggerBase, Swagger}
import org.scalatra.ScalatraServlet
import org.json4s.{DefaultFormats, Formats}

class ResourcesApp(implicit val swagger: Swagger) extends ScalatraServlet with NativeSwaggerBase {
  implicit override val jsonFormats: Formats = DefaultFormats
}

class LcdSwagger extends Swagger("1.0", "1")
