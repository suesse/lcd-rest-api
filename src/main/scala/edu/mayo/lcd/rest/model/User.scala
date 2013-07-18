package edu.mayo.lcd.rest.model

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession

object UserColumns extends Enumeration {
  type UserColumns = Value
  val id, lanId, enabled = Value
}

case class
User(id: Int, name: String, role: String, enabled: Boolean)

object User extends Table[(Int, String, Boolean)]("USER") {
  def id = column[Int](UserColumns.id.toString, O.PrimaryKey)
  def lanId = column[String](UserColumns.lanId.toString, O.NotNull)
  def enabled = column[Boolean](UserColumns.enabled.toString, O.NotNull)
  def * = id ~ lanId ~ enabled

  def getAllUsers(role: Option[String], enabledOnly: Option[Boolean], database: Database): List[User] = {
    if (role.isDefined)
      getAllUsersByRole(role.get, enabledOnly)
    else
      getAllUsers(enabledOnly, database)
  }

  private def getAllUsers(enabledOnly: Option[Boolean], database: Database): List[User] = {
    var users = List[User]()
      if (enabledOnly.isDefined) {

        val enabledUsersQuery = for {
          en <- Parameters[Boolean]
          u <- User if u.enabled is en
        } yield u.id

        val tusers = enabledUsersQuery(enabledOnly.get)
        println(tusers)

//        if (enabledOnly.get) {
//          /* TODO: return only enabled users */
//          println("role=None,enabled=true")
//          database withSession {
//
//          }
//        }
//        else {
//          /* TODO: return only disabled users */
//          println("role=None,enabled=false")
//        }
      } else {
        /* TODO: return ALL users */
        println("role=None,enabled=None")
        database withSession {
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