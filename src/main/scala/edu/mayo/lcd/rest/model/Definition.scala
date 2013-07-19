package edu.mayo.lcd.rest.model

import java.util.Calendar

case class Definition(id: String, uploader: Int, date: Calendar) {
  var properties = List[(String, String)]()
  var keywords = List[String]()
  var valueSets = List[(String, String)]()
  var comments = List[DefinitionComment]()

  def addKeyword(keyword: String) {
    keywords ::= keyword
  }

  def addProperty(property: String, value: String) {
    properties ::=(property, value)
  }

  def addComment(comment: DefinitionComment) {
    comments ::= comment
  }

  def addValueSet(valueSet: String, version: String) {
    valueSets ::=(valueSet, version)
  }
}

case class DefinitionComment(id: Option[Int],
                             definitionId: String,
                             author: Int,
                             comment: String,
                             date: Option[Calendar])

trait DefinitionRepo extends LcdRepo[Definition]
