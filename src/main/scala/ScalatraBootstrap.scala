import com.mchange.v2.c3p0.ComboPooledDataSource
import edu.mayo.lcd.rest.controller._
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.LoggerFactory
import scala.slick.session.Database

class ScalatraBootstrap extends LifeCycle {

  val logger = LoggerFactory.getLogger(getClass)
  val cpds = new ComboPooledDataSource
  implicit val db = Database.forDataSource(cpds)

  override def init(context: ServletContext) {
//    implicit val swagger = new LcdSwagger
    context.mount(new UsersController, "/users/*")
    context.mount(new DefinitionsController, "/definitions/*")
    context.mount(new KeywordsController, "/keywords/*")
    context.mount(new PropertiesController, "/properties/*")
    //    context.mount(new AuditLogsController, "/auditlogs/*")
//    context mount(new ResourcesApp, "/api-docs/*")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    cpds.close
  }

}
