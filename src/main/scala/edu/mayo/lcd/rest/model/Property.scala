package edu.mayo.lcd.rest.model

import scala.slick.driver.MySQLDriver.simple._
import java.sql.Date

object PropertyColumns extends Enumeration {
  type PropertyColumns = Value
  val id, property, userDefinable = Value
}

case class Property(id: Int, property: String, creator: Int, date: Date, value: String)

object Property extends Table[(Int, String, Boolean)]("PROP") {
  def id = column[Int](PropertyColumns.id.toString, O.PrimaryKey)
  def property = column[String](PropertyColumns.property.toString, O.NotNull)
  def userDefinable = column[Boolean](PropertyColumns.userDefinable.toString, O.NotNull)
  def * = id ~ property ~ userDefinable
}

object PropertyValueColumns extends Enumeration {
  type PropertyValueColumns = Value
  val id, propertyId, value = Value
}

object PropertyValue extends Table[(Int, Int, String)]("PROP_VAL") {
  def id = column[Int](PropertyValueColumns.id.toString, O.PrimaryKey)
  def propertyId = column[Int](PropertyValueColumns.propertyId.toString, O.NotNull)
  def value = column[String](PropertyValueColumns.value.toString, O.NotNull)
  def * = id ~ propertyId ~ value
  def property = foreignKey("fk_propval_propId", propertyId, Property)(_.id)
}