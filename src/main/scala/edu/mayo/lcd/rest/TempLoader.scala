package edu.mayo.lcd.rest

import edu.mayo.lcd.rest.model.{DefinitionComment, Definition}

import java.util.{Calendar, UUID}
import java.sql.Date

import org.apache.poi.ss.usermodel.{Cell, Workbook, WorkbookFactory}

import scala.collection.JavaConversions._
import java.io.{InputStream, File}
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.{BasicCredentialsProvider, DefaultHttpClient}
import org.apache.http.params.CoreProtocolPNames
import org.apache.http.HttpVersion
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.auth.{UsernamePasswordCredentials, AuthScope}
import org.apache.http.client.protocol.ClientContext
import org.apache.http.entity.mime.{MultipartEntity, HttpMultipartMode}
import org.apache.http.entity.mime.content.FileBody
import javax.xml.bind.{Unmarshaller, JAXBContext}
import edu.mayo.lcd.rest.cts2.ValueSetImportResultType

class TempLoader {

  def load(path: String) {
    val file = new File(path)
    val wb = WorkbookFactory.create(file)
    var definition = parseWorkbook(wb,
      new Definition(UUID.randomUUID().toString, 101, new Date(Calendar.getInstance.getTimeInMillis)))
    definition = insertValueSets(file, definition)
    // TODO: save definition
  }

  def parseWorkbook(wb: Workbook, definition: Definition) = {
    val propSheet = wb.getSheet("Details")
    propSheet.rowIterator foreach (row => {
      val prop = getCellValue(row.getCell(0))
      val value = getCellValue(row.getCell(1))
      if (prop.equalsIgnoreCase("keyword")) {
        definition.addKeyword(value)
      } else if (prop.equalsIgnoreCase("comment")) {
        definition.addComment(new DefinitionComment(0, "", 101, value, new Date(Calendar.getInstance.getTimeInMillis)))
      } else {
        definition.addProperty(prop, value)
      }
    })
    definition
  }

  def getCellValue(cell:Cell) = {
    if (cell != null) {
      val cellType = cell.getCellType

      cellType match {
        case Cell.CELL_TYPE_STRING => cell.getStringCellValue
        case Cell.CELL_TYPE_NUMERIC => cell.getNumericCellValue.asInstanceOf[Int].toString
        case Cell.CELL_TYPE_BLANK => null
        case _ => throw new IllegalStateException("Found a Cell of type: " + cellType)
      }
    } else {
      null
    }
  }

  def insertValueSets(file: File, definition: Definition) = {
    val post = new HttpPost("http://127.0.0.1:8080/cts2/upload/cts2spreadsheet")
    val client = new DefaultHttpClient
    client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1)
    val context = new BasicHttpContext
    val credsProvider = new BasicCredentialsProvider
    credsProvider.setCredentials(
      AuthScope.ANY, new UsernamePasswordCredentials("dsuesse", "cts2Mayo()"))
    context.setAttribute(ClientContext.CREDS_PROVIDER, credsProvider)

    val entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
    entity.addPart("file", new FileBody(file, "application/vnd.ms-excel"))
    post.setEntity(entity)

    val cts2Response = client.execute(post, context)
    if (cts2Response.getStatusLine.getStatusCode != 200) {
      println("ERROR!")
    } else {
      val ent = cts2Response.getEntity
      if (ent != null) {
        val instream = ent.getContent
        setValueSets(instream, definition)
      }
    }

    client.getConnectionManager.shutdown
    definition
  }

  def setValueSets(xmlStream: InputStream, definition: Definition) = {
    val context = JAXBContext.newInstance(classOf[ValueSetImportResultType])
    val unmarshaller = context.createUnmarshaller
    val importResult = unmarshaller.unmarshal(xmlStream).asInstanceOf[ValueSetImportResultType]
    importResult.getValueSets.getValueSet.foreach(vs => definition.addValueSet(vs.getName(), vs.getVersion()))
    xmlStream.close
    definition
  }

}
