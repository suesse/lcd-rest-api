package edu.mayo.lcd.rest.model

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession
import java.sql.Date

case class Definition(id: String, uploader: Int, date: Date) {
  var properties: List[(String, String)] = _
  var keywords: List[String] = _
  var valueSet: List[(String, String)] = _
  var comments: List[DefinitionComment] = _

  def addKeyword(keyword: String) { keywords ::= keyword }
  def addProperty(property: String, value: String) { properties ::= (property, value) }
}

object Definition extends Table[(String, Int, Date)]("DEF"){
  def id = column[String]("id", O.PrimaryKey)
  def uploaderId = column[Int]("uploader", O.NotNull)
  def uploaded = column[Date]("uploaded", O.NotNull)
  def * = id ~ uploaderId ~ uploaded
  def uploader = foreignKey("fk_def_uploader", uploaderId, User)(_.id)

  val keywordQuery = for {
    k <- Keyword
    kwv <- DefinitionKeywords if kwv.definitionId eq id
  } yield k

  val propertyQuery = for {
    dp <- DefinitionProperty if dp.definitionId eq id
    pv <- PropertyValue if pv.id eq dp.propertyValueId
    p <- Property if p.id eq pv.propertyId
  } yield (p.property, pv.value)

  def getAll(db: Database) {
    var definitions = List[Definition]()
    db withSession {
      Query(Definition) foreach { case (id, lanId, date) =>
        val definition = new Definition(id, lanId, date)
        keywordQuery.foreach(k => definition.addKeyword(k._2))
        propertyQuery.foreach(p => definition.addProperty(p._1, p._2))
//        val commentQuery
//        val valuesetQuery
        definitions ::= definition
      }
    }
  }
}

object DefinitionProperty extends Table[(String, Int)]("DEF_PROP") {
  def definitionId = column[String]("definitionId")
  def propertyValueId = column[Int]("propertyValueId")
  def * = definitionId ~ propertyValueId
  def definition = foreignKey("fk_defProp_defId", definitionId, Definition)(_.id)
  def propertyValue = foreignKey("fk_defProp_propertyValueId", propertyValueId, PropertyValue)(_.id)
}

//object DefinitionProperties extends Table[(String, String, String)]("definition_properties") {
//  def definitionId = column[String]("definitionId", O.NotNull)
//  def property = column[String]("property", O.NotNull)
//  def value = column[String]("value")
//  def * = definitionId ~ property ~ value
//}

object DefinitionKeywords extends Table[(String, Int)]("DEF_KW") {
  def definitionId = column[String]("definitionId", O.NotNull)
  def keywordId = column[Int]("keywordId", O.NotNull)
  def * = definitionId ~ keywordId
  def keyword = foreignKey("fk_defkw_keywordId", keywordId, Keyword)(_.id)
  def definition = foreignKey("fk_defkw_definitionId", definitionId, Definition)(_.id)
}

case class DefinitionComment(id: Int, definitionId: String, author: Int, comment: String, date: Date)

// DEF_CMT, DEF_VS
object DefinitionComments extends Table[(Int, String, Int, Date)]("DEF_CMNT") {
  def id = column[Int]("id", O.NotNull)
  def definitionId = column[String]("definitionId", O.NotNull)
  def author = column[Int]("author", O.NotNull)
  def comment = column[String]("comment", O.NotNull)
  def date = column[Date]("date", O.NotNull)
  def * = id ~ definitionId ~ author ~ date
  def definition = foreignKey("fk_defcmnt_definitionId", definitionId, Definition)(_.id)
  def user = foreignKey("fk_defcmnt_userId", author, User)(_.id)
}

object DefinitionValueSets extends Table[(String, String, String)]("DEF_VS") {
  def definitionId = column[String]("definitionId", O.NotNull)
  def valueSet = column[String]("valueset", O.NotNull)
  def version = column[String]("version", O.NotNull)
  def * = definitionId ~ valueSet ~ version
  def definition = foreignKey("fk_defvs_definitionId", definitionId, Definition)(_.id)
}
