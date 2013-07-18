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
import scala.collection.JavaConversions._
import edu.mayo.lcd.rest.model._

class Indexer {

  val index = new SimpleFSDirectory(new File("/Users/m091355/Downloads/lucene"))

  def indexDefs = {
    val analyzer = new StandardAnalyzer(Version.LUCENE_43)
    val iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer)
    val writer = new IndexWriter(index, iwc)
    Database.forURL("jdbc:mysql://localhost:3306/lcd_rest", user = "root", password = "root", driver = "com.mysql.jdbc.Driver") withSession {
      Query(Definition) foreach { case (id, uploader, date) =>
        val doc = new Document
        doc.add(new StringField("id", id, Field.Store.YES))
        doc.add(new IntField("uploader", uploader, Field.Store.YES))
        doc.add(new LongField("date", date.getTime, Field.Store.YES))
        Definition.getDefKeywords(id).map(kw => doc.add(new StringField("keyword", kw, Field.Store.YES)))
        Definition.getDefProperties(id).map(p => doc.add(new StringField(p._1, p._2, Field.Store.YES)))
        writer.addDocument(doc)
      }
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
//      println(d.get(lField) + "\t" + d.get("id"))
      d.getFields foreach (f => {
        print(f.name() + ":" + f.stringValue())
      })
    })
    reader.close()
  }

}
