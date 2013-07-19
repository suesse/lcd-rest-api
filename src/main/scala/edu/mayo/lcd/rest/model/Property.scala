package edu.mayo.lcd.rest.model

import java.sql.Date

case class Property(id: Int, property: String, governed: Boolean)

trait PropertyRepo extends LcdRepo[Property]
