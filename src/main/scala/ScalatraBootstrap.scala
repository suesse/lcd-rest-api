import edu.mayo.lcd.rest.controller._
import edu.mayo.lcd.rest.DatabaseInit
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.LoggerFactory

class ScalatraBootstrap extends LifeCycle with DatabaseInit {

  val logger = LoggerFactory.getLogger(getClass)

  implicit val swagger = new LcdSwagger

  override def init(context: ServletContext) {
    context.mount(new UsersController, "/users/*")
    context.mount(new DefinitionsController, "/definitions/*")
    context.mount(new KeywordsController, "/keywords/*")
//    context.mount(new PropertiesController, "/properties/*")
//    context.mount(new AuditLogsController, "/auditlogs/*")
    context mount(new ResourcesApp, "/api-docs/*")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDatabase()
  }

}
