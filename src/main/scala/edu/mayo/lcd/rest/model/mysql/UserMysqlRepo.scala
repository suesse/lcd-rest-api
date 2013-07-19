package edu.mayo.lcd.rest.model.mysql

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession
import edu.mayo.lcd.rest.model.{UserRepo, User}

class UserMysqlRepo(implicit db: Database) extends UserRepo {
  def getAll = getAllUsers(None, None, db)

  def get(id: Any) = ???

  def create(resource: User) = ???

  def update(resource: User) = ???

  def delete(resource: User) = ???

  def getAll(role: Option[String], enabledOnly: Option[Boolean], database: Database) = getAllUsers(role, enabledOnly, db)

  private def getAllUsers(role: Option[String], enabledOnly: Option[Boolean], database: Database): Option[List[User]] = {
    if (role.isDefined)
      getAllUsersByRole(role.get, enabledOnly)
    else
      getAllUsers(enabledOnly, database)
  }

  private def getAllUsers(enabledOnly: Option[Boolean], database: Database): Option[List[User]] = {
    var users = List[User]()
    if (enabledOnly.isDefined) {

      val enabledUsersQuery = for {
        en <- Parameters[Boolean]
        u <- UserTable if u.enabled is en
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
        Query(UserTable) foreach {
          case (id, lanId, enabled) =>
            val usr = new User(id, lanId, "admin", enabled)
            users ::= usr
        }
      }
    }
    Option(users)
  }

  private def getAllUsersByRole(role: String, enabledOnly: Option[Boolean]): Option[List[User]] = {
    if (enabledOnly.isDefined) {
      if (enabledOnly.get) {
        /* TODO: return only enabled users with role */
        println("role=" + role + ",enabled=true")
      }
      else {
        /* TODO: return only disabled users with role */
        println("role=" + role + ",enabled=false")
      }
    } else {
      /* TODO: return ALL users with role */
      println("role=" + role + ",enabled=None")
    }
    Option(List[User]())
  }
}

private object UserColumns extends Enumeration {
  type UserColumns = Value
  val id, lanId, enabled = Value
}

object UserTable extends Table[(Int, String, Boolean)]("USER") {
  def id = column[Int](UserColumns.id.toString, O.PrimaryKey)
  def lanId = column[String](UserColumns.lanId.toString, O.NotNull)
  def enabled = column[Boolean](UserColumns.enabled.toString, O.NotNull)
  def * = id ~ lanId ~ enabled
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