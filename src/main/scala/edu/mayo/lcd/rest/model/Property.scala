package edu.mayo.lcd.rest.model

import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date

case class Property(id: Int, property: String, creator: Int, date: Date, value: String)

object Property extends Table[(Int, String, Boolean)]("PROP") {
  def id = column[Int]("id", O.PrimaryKey)
  def property = column[String]("property", O.NotNull)
  def userDefinable = column[Boolean]("userDefinable", O.NotNull)
  def * = id ~ property ~ userDefinable
}

object PropertyValue extends Table[(Int, Int, String)]("PROP_VAL") {
  def id = column[Int]("id", O.PrimaryKey)
  def propertyId = column[Int]("propertyId", O.NotNull)
  def value = column[String]("value", O.NotNull)
  def * = id ~ propertyId ~ value
  def property = foreignKey("fk_propval_propId", propertyId, Property)(_.id)
}