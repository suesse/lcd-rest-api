package edu.mayo.lcd.rest.model

import java.sql.Date
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession

object KeywordColumns extends Enumeration {
  type KeywordColumns = Value
  val id, keyword, creator, date = Value
}

case class Keyword(id: Int, keyword: String, userId: Int, date: Date)

object Keyword extends Table[(Int, String, Int, Date)]("keyword") {
  def id = column[Int](KeywordColumns.id.toString, O.PrimaryKey)
  def keyword = column[String](KeywordColumns.keyword.toString, O.NotNull)
  def userId = column[Int](KeywordColumns.creator.toString)
  def date = column[Date](KeywordColumns.date.toString, O.NotNull)
  def * = id ~ keyword ~ userId ~ date
  def user = foreignKey("fk_keyword_user", userId, User)(_.id)

  def getAllKeywords:List[Keyword] = {
    val keywords = List[Keyword]()
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      Query(Keyword) foreach { case (id, keyword, userId, date) =>
        keywords :+ new Keyword(id, keyword, userId, date)
      }
    }
    keywords
  }
}

object DefinitionKeywordsTable extends Table[(String, String)]("definition_keywords") {
  def definitionId = column[String]("definitionId", O.NotNull)
  def keyword = column[String]("keyword", O.NotNull)
  def * = definitionId ~ keyword
}