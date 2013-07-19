package edu.mayo.lcd.rest.model.mysql

import edu.mayo.lcd.rest.model.{KeywordRepo, Keyword}
import java.sql.Date
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession
import java.util.Calendar

class KeywordMysqlRepo(implicit db: Database) extends KeywordRepo {

  def getAll = {
    val keywords = List[Keyword]()
    db withSession {
      Query(KeywordTable) foreach {
        case (id, keyword, userId, date) =>
          keywords :+ new Keyword(id, keyword, userId, {
            val c = Calendar.getInstance()
            c.setTime(date)
            c
          })
      }
    }
    Option(keywords)
  }

  def get(id: Any) = {
    db withSession {
      val keywordQuery = for {
        k <- KeywordTable if k.id is id.asInstanceOf[Int]
      } yield k
      keywordQuery.firstOption map (k => new Keyword(k._1, k._2, k._3, {
        val c = Calendar.getInstance
        c.setTime(k._4)
        c
      }))
    }
  }

  def getByKeyword(keyword: String) = {
    db withSession {
      val keywordQuery = for {
        k <- KeywordTable if k.keyword is keyword
      } yield k
      keywordQuery.firstOption map (k => new Keyword(k._1, k._2, k._3, {
        val c = Calendar.getInstance
        c.setTime(k._4)
        c
      }))
    }
  }

  def create(resource: Keyword) = ???

  def update(resource: Keyword) = ???

  def delete(resource: Keyword) = ???

}

object KeywordColumns extends Enumeration {
  type KeywordColumns = Value
  val id, keyword, creator, date = Value
}

object KeywordTable extends Table[(Int, String, Int, Date)]("keyword") {
  def id = column[Int](KeywordColumns.id.toString, O.PrimaryKey)
  def keyword = column[String](KeywordColumns.keyword.toString, O.NotNull)
  def userId = column[Int](KeywordColumns.creator.toString)
  def date = column[Date](KeywordColumns.date.toString, O.NotNull)
  def * = id ~ keyword ~ userId ~ date
  def user = foreignKey("fk_keyword_user", userId, UserTable)(_.id)
}