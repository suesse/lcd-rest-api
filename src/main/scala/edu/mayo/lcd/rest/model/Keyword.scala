package edu.mayo.lcd.rest.model

import java.util.Calendar

case class Keyword(id: Int, keyword: String, userId: Int, date: Calendar)

trait KeywordRepo extends LcdRepo[Keyword] {
  def getByKeyword(keyword: String): Option[Keyword]
}
