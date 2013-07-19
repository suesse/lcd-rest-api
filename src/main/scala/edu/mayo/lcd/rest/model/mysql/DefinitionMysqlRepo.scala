package edu.mayo.lcd.rest.model.mysql

import java.sql.Date
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Database
import Database.threadLocalSession
import edu.mayo.lcd.rest.model.{DefinitionComment, DefinitionRepo, Definition}
import java.util.Calendar

class DefinitionMysqlRepo(implicit db: Database) extends DefinitionRepo {

  def getAll = {
    var definitions = List[Definition]()

    db withSession {
      Query(DefinitionTable) foreach {
        case (id, uploader, date) =>
          definitions ::= createDefinition(id, uploader, date)
      }
    }
    Option(definitions)
  }

  def get(id: Any) = ???

  def create(resource: Definition) = ???

  def update(resource: Definition) = ???

  def delete(resource: Definition) = ???
  def createDefinition(id: String, uploader: Int, date: Date) = {
    val definition = new Definition(id, uploader, {
      val c = Calendar.getInstance()
      c.setTime(date)
      c
    })
    definition.keywords = getDefKeywords(id)
    definition.properties = getDefProperties(id)
    definition.comments = getDefComments(id)
    definition.valueSets = getDefValueSets(id)
    definition
  }

  def getDefKeywords(definitionId: String) = {
    var keywords = List[String]()
    val keywordQuery = for {
      k <- KeywordTable
      kwv <- DefinitionKeywords if kwv.definitionId is definitionId
    } yield k.keyword
    keywordQuery.list.map(kw => keywords ::= kw)
    keywords
  }

  def getDefProperties(definitionId: String) = {
    var properties = List[(String, String)]()
    val propertyQuery = for {
      dp <- DefinitionProperty if dp.definitionId is definitionId
      pv <- PropertyValueTable if pv.id is dp.propertyValueId
      p <- PropertyTable if p.id is pv.propertyId
    } yield (p.property, pv.value)
    propertyQuery.list.map(p => properties ::=(p._1, p._2))
    properties
  }

  def getDefComments(definitionId: String) = {
    var comments = List[DefinitionComment]()
    val commentQuery = for {
      c <- DefinitionComments if c.definitionId is definitionId
    } yield c
    commentQuery.list.map(c => comments ::= new DefinitionComment(Option(c._1), c._2, c._3, c._4, Option({
      val cal = Calendar.getInstance()
      cal.setTime(c._5)
      cal
    })))
    comments
  }

  def getDefValueSets(definitionId: String) = {
    var valuesets = List[(String, String)]()
    val valueSetsQuery = for {
      vs <- DefinitionValueSets if vs.definitionId is definitionId
    } yield (vs.valueSet, vs.version)
    valueSetsQuery.list.map(vs => valuesets ::=(vs._1, vs._2))
    valuesets
  }
  //  def save() {
  //    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
  //      Definition.insert(id, uploader, date)
  //      comments.foreach(comment => DefinitionComments.forInsert insert comment)
  //      valueSets.foreach(valueSet => DefinitionValueSets.insert(id, valueSet._1, valueSet._2))
  //      // TODO: if keyword !exist then fail
  //      keywords.foreach(keyword => DefinitionKeywords.insert(id, Keyword.getKeyword(keyword).get.id))
  //      //      properties.foreach(property => PropertyValue)
  //    }
  //  }
}

object DefinitionColumns extends Enumeration {
  type DefinitionColumns = Value
  val id, uploader, uploaded = Value
}

object DefinitionTable extends Table[(String, Int, Date)]("DEF") {
  def id = column[String](DefinitionColumns.id.toString, O.PrimaryKey)

  def uploaderId = column[Int](DefinitionColumns.uploader.toString, O.NotNull)

  def uploaded = column[Date](DefinitionColumns.uploaded.toString, O.NotNull)

  def * = id ~ uploaderId ~ uploaded

  def uploader = foreignKey("fk_def_uploader", uploaderId, UserTable)(_.id)
}

object DefinitionPropertyColumns extends Enumeration {
  type DefinitionPropertyColumns = Value
  val definitionId, propertyValueId = Value
}

object DefinitionProperty extends Table[(String, Int)]("DEF_PROP") {
  def definitionId = column[String](DefinitionPropertyColumns.definitionId.toString)

  def propertyValueId = column[Int](DefinitionPropertyColumns.propertyValueId.toString)

  def * = definitionId ~ propertyValueId

  def definition = foreignKey("fk_defProp_defId", definitionId, DefinitionTable)(_.id)

  def propertyValue = foreignKey("fk_defProp_propertyValueId", propertyValueId, PropertyValueTable)(_.id)
}

object DefinitionKeywordsColumns extends Enumeration {
  type DefinitionKeywordsColumns = Value
  val definitionId, keywordId = Value
}

object DefinitionKeywords extends Table[(String, Int)]("DEF_KW") {
  def definitionId = column[String](DefinitionKeywordsColumns.definitionId.toString, O.NotNull)

  def keywordId = column[Int](DefinitionKeywordsColumns.keywordId.toString, O.NotNull)

  def * = definitionId ~ keywordId

  def keyword = foreignKey("fk_defkw_keywordId", keywordId, KeywordTable)(_.id)

  def definition = foreignKey("fk_defkw_definitionId", definitionId, DefinitionTable)(_.id)
}

object DefinitionCommentColumns extends Enumeration {
  type DefinitionCommentColumns = Value
  val id, definitionId, author, comment, date = Value
}

// DEF_CMT, DEF_VS
object DefinitionComments extends Table[(Int, String, Int, String, Date)]("DEF_CMNT") {
  def id = column[Int](DefinitionCommentColumns.id.toString, O.NotNull)

  def definitionId = column[String](DefinitionCommentColumns.definitionId.toString, O.NotNull)

  def author = column[Int](DefinitionCommentColumns.author.toString, O.NotNull)

  def comment = column[String](DefinitionCommentColumns.comment.toString, O.NotNull)

  def date = column[Date](DefinitionCommentColumns.date.toString, O.NotNull)

  def * = id ~ definitionId ~ author ~ comment ~ date

  def definition = foreignKey("fk_defcmnt_definitionId", definitionId, DefinitionTable)(_.id)

  def user = foreignKey("fk_defcmnt_userId", author, UserTable)(_.id)

  def forInsert = definitionId ~ author ~ comment <>
    ( {
      t => DefinitionComment(None, t._1, t._2, t._3, None)
    }, {
      (dc: DefinitionComment) => Some((dc.definitionId, dc.author, dc.comment))
    })
}

object DefinitionValueSetsColumns extends Enumeration {
  type DefinitionValueSetsColumns = Value
  val definitionId, valueset, version = Value
}

object DefinitionValueSets extends Table[(String, String, String)]("DEF_VS") {
  def definitionId = column[String](DefinitionValueSetsColumns.definitionId.toString, O.NotNull)

  def valueSet = column[String](DefinitionValueSetsColumns.valueset.toString, O.NotNull)

  def version = column[String](DefinitionValueSetsColumns.version.toString, O.NotNull)

  def * = definitionId ~ valueSet ~ version

  def definition = foreignKey("fk_defvs_definitionId", definitionId, DefinitionTable)(_.id)
}
