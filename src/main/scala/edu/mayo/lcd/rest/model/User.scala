package edu.mayo.lcd.rest.model

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession

object UserColumns extends Enumeration {
  type UserColumns = Value
  val id, lanId, enabled = Value
}

case class User(id: Int, name: String, role: List[String], enabled: Boolean)

object User extends Table[(Int, String, Boolean)]("user") {
  def id = column[Int](UserColumns.id.toString, O.PrimaryKey)
  def lanId = column[String](UserColumns.lanId.toString, O.NotNull)
  def enabled = column[Boolean](UserColumns.enabled.toString, O.NotNull)
  def * = id ~ lanId ~ enabled

  def getAllUsers(role: Option[String], enabledOnly: Option[Boolean]): List[User] = {
    if (role.isDefined)
      getAllUsersByRole(role.get, enabledOnly)
    else
      getAllUsers(enabledOnly)
  }

  private def getAllUsers(enabledOnly: Option[Boolean]): List[User] = {
    var users = List[User]()
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      if (enabledOnly.isDefined) {
        if (enabledOnly.get) {
          /* TODO: return only enabled users */
          println("role=None,enabled=true")
        }
        else {
          /* TODO: return only disabled users */
          println("role=None,enabled=false")
        }
      } else {
        /* TODO: return ALL users */
        println("role=None,enabled=None")
        Query(User) foreach { case (id, lanId, enabled) =>
          val usr = new User(id, lanId, "admin", enabled)
          users ::= usr
        }
      }
    }
    users
  }

  private def getAllUsersByRole(role: String, enabledOnly: Option[Boolean]): List[User] = {
    if (enabledOnly.isDefined) {
      if (enabledOnly.get) {
        /* TODO: return only enabled users with role */
        println("role="+role+",enabled=true")
      }
      else {
        /* TODO: return only disabled users with role */
        println("role="+role+",enabled=false")
      }
    } else {
      /* TODO: return ALL users with role */
      println("role="+role+",enabled=None")
    }
    List[User]()
  }

  private def getRoleMap: Map[String, String] = {
    Map[String, String]()
  }

}

object UserRolesColumns extends Enumeration {
  type UserRolesColumns = Value
  val id, role = Value
}

object UserRoles extends Table[(Int, String)]("userRoles") {
  def id = column[Int](UserRolesColumns.id.toString, O.PrimaryKey)
  def role = column[String](UserRolesColumns.role.toString)
  def * = id ~ role
}