package edu.mayo.lcd.rest.model.mysql

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession
import edu.mayo.lcd.rest.model.{Property, PropertyRepo}

class PropertyMysqlRepo(implicit db: Database) extends PropertyRepo {
  def getAll = {
    var properties = List[Property]()
    db withSession {
      Query(PropertyTable) foreach {
        case (id, property, governed) =>
          properties ::= new Property(id, property, governed)
      }
    }
    Option(properties)
  }

  def get(id: Any) = ???

  def create(resource: Property) = ???

  def update(resource: Property) = ???

  def delete(resource: Property) = ???
}

object PropertyColumns extends Enumeration {
  type PropertyColumns = Value
  val id, property, governed = Value
}

object PropertyTable extends Table[(Int, String, Boolean)]("PROP") {
  def id = column[Int](PropertyColumns.id.toString, O.PrimaryKey)
  def property = column[String](PropertyColumns.property.toString, O.NotNull)
  def governed = column[Boolean](PropertyColumns.governed.toString, O.NotNull)
  def * = id ~ property ~ governed
}

object PropertyValueColumns extends Enumeration {
  type PropertyValueColumns = Value
  val id, propertyId, value, governed = Value
}

object PropertyValueTable extends Table[(Int, Int, String, Boolean)]("PROP_VAL") {
  def id = column[Int](PropertyValueColumns.id.toString, O.PrimaryKey)
  def propertyId = column[Int](PropertyValueColumns.propertyId.toString, O.NotNull)
  def value = column[String](PropertyValueColumns.value.toString, O.NotNull)
  def governed = column[Boolean](PropertyColumns.governed.toString, O.NotNull)
  def * = id ~ propertyId ~ value ~ governed
  def property = foreignKey("fk_propval_propId", propertyId, PropertyTable)(_.id)
}