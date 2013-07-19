package edu.mayo.lcd.rest.model

import scala.slick.session.Database

case class User(id: Int, name: String, role: String, enabled: Boolean)

trait UserRepo extends LcdRepo[User] {
  def getAll(role: Option[String], enabledOnly: Option[Boolean], database: Database): Option[List[User]]
}
