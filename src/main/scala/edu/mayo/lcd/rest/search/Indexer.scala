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
import edu.mayo.lcd.rest.model._

class Indexer {

  def indexDefs = {
    println("Definitions:")
    val analyzer = new StandardAnalyzer(Version.LUCENE_43)
    val iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer)
    val writer = new IndexWriter(index, iwc)
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      Query(Definition) foreach { case (id, uploader, date) =>
        val doc = new Document
        doc.add(new StringField("id", id, Field.Store.YES))

        val keywordQuery = for {
          k <- Keyword
          kwv <- DefinitionKeywords if kwv.definitionId eq id
        } yield k
        keywordQuery.foreach(k => doc.add(new StringField("keyword", k._2, Field.Store.YES)))
        val propertyQuery = for {
          dp <- DefinitionProperty if dp.definitionId eq id
          pv <- PropertyValue if pv.id eq dp.propertyValueId
          p <- Property if p.id eq pv.propertyId
        } yield (p.property, pv.value)
        propertyQuery.foreach(p => doc.add(new StringField(p._1, p._2, Field.Store.YES)))

        writer.addDocument(doc)
      }
      writer.close()
    }
  }

  def selectDefKeywords = {
    println("Keywords:")
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      Query(DefinitionKeywords) foreach { case (definitionId, keyword) =>
        println(" " + definitionId + "\t" + keyword)
      }
    }
  }

//  def selectDefProperties = {
//    println("Properties:")
//    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
//      Query(DefinitionProperties) foreach { case (definitionId, property, value) =>
//        println(" " + definitionId + "\t" + property + "\t" + property)
//      }
//    }
//  }

  val index = new SimpleFSDirectory(new File("/Users/m091355/Downloads/lucene"))
//  val index = new RAMDirectory()
//  def indexAll {
//    val analyzer = new StandardAnalyzer(Version.LUCENE_43)
//    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
//      val iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer)
//      val writer = new IndexWriter(index, iwc)
//
//      println("Indexing Definition Keywords...")
//      var i = 0
//      Query(DefinitionKeywords) foreach { case (definitionId, keyword) =>
//        val doc = new Document
//        doc.add(new StringField("id", definitionId, Field.Store.YES))
//        doc.add(new TextField("keyword", keyword, Field.Store.YES))
//        writer.addDocument(doc)
//        i+=1
//      }
//      println("Indexed " + i + " keywords.")
//
//      println("Indexing Definition Properties...")
//      i = 0
//      Query(DefinitionProperties) foreach { case (definitionId, property, value) =>
//        val doc = new Document
//        doc.add(new StringField("id", definitionId, Field.Store.YES))
//        doc.add(new TextField(property.toLowerCase, value, Field.Store.YES))
//        writer.addDocument(doc)
//        i+=1
//      }
//      println("Indexed " + i + " properties.")
//
//      writer.close()
//    }
//  }

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
