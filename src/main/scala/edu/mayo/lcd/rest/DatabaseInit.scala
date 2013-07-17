package edu.mayo.lcd.rest

import com.mchange.v2.c3p0.ComboPooledDataSource
import scala.slick.session.Database

trait DatabaseInit {

  val cpds = new ComboPooledDataSource
  implicit val db = Database.forDataSource(cpds)

  def closeDatabase() {
    cpds.close
  }
}
