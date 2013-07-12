package edu.mayo.lcd.rest.search

import scala.slick.session.Database
import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession
import org.apache.lucene.document._
import org.apache.lucene.index.{DirectoryReader, IndexWriterConfig, IndexWriter}
import org.apache.lucene.store.{SimpleFSDirectory, RAMDirectory}
import org.apache.lucene.util.Version
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.search.{TopScoreDocCollector, IndexSearcher}
import org.apache.lucene.queryparser.classic.QueryParser
import java.io.File
import edu.mayo.lcd.rest.model.DefinitionKeywordsTable

class Indexer {

  object Definition extends Table[(String, String, String, String)]("definition") {
    def id = column[String]("id", O.PrimaryKey)
    def keyword = column[String]("keyword", O.NotNull)
    def property = column[String]("property", O.NotNull)
    def value = column[String]("value")
    def * = id ~ keyword ~ property ~ value
  }

  object DefinitionProperties extends Table[(String, String, String)]("definition_properties") {
    def definitionId = column[String]("definitionId", O.NotNull)
    def property = column[String]("property", O.NotNull)
    def value = column[String]("value")
    def * = definitionId ~ property ~ value
  }

  def selectDefs = {
    println("Definitions:")
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      Query(Definition) foreach { case (id, keyword, property, value) =>
        println(" " + id + "\t" + keyword + "\t" + property + "\t" + value)
      }
    }
  }

  def selectDefKeywords = {
    println("Keywords:")
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      Query(DefinitionKeywordsTable) foreach { case (definitionId, keyword) =>
        println(" " + definitionId + "\t" + keyword)
      }
    }
  }

  def selectDefProperties = {
    println("Properties:")
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      Query(DefinitionProperties) foreach { case (definitionId, property, value) =>
        println(" " + definitionId + "\t" + property + "\t" + property)
      }
    }
  }

//  val index = new SimpleFSDirectory(new File("/Users/m091355/Downloads/lucene"))
  val index = new RAMDirectory()
  def indexAll {
    val analyzer = new StandardAnalyzer(Version.LUCENE_43)
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      val iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer)
      val writer = new IndexWriter(index, iwc)

      println("Indexing Definition Keywords...")
      var i = 0
      Query(DefinitionKeywordsTable) foreach { case (definitionId, keyword) =>
        val doc = new Document
        doc.add(new StringField("id", definitionId, Field.Store.YES))
        doc.add(new TextField("keyword", keyword, Field.Store.YES))
        writer.addDocument(doc)
        i+=1
      }
      println("Indexed " + i + " keywords.")

      println("Indexing Definition Properties...")
      i = 0
      Query(DefinitionProperties) foreach { case (definitionId, property, value) =>
        val doc = new Document
        doc.add(new StringField("id", definitionId, Field.Store.YES))
        doc.add(new TextField(property.toLowerCase, value, Field.Store.YES))
        writer.addDocument(doc)
        i+=1
      }
      println("Indexed " + i + " properties.")

      writer.close()
    }
  }

  def search(field: String, q: String) {
    val lField = field.toLowerCase
    println("Querying " + lField + " for " + q + "...")

    val reader = DirectoryReader.open(index)
    println("Documents with field " + lField + ": " + reader.getDocCount(lField))
    val searcher = new IndexSearcher(reader)
    val analyzer = new StandardAnalyzer(Version.LUCENE_43)
    val collector = TopScoreDocCollector.create(10, true)
    val query = new QueryParser(Version.LUCENE_43, lField, analyzer).parse(q)
    searcher.search(query, collector)
    val hits = collector.topDocs().scoreDocs

    println("Found " + hits.length + " hits.")
    hits foreach (hit => {
      val d = searcher.doc(hit.doc)
      println(d.get(lField) + "\t" + d.get("id"))
    })
    reader.close()
  }

}
