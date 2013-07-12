import com.mchange.v2.c3p0.ComboPooledDataSource
import edu.mayo.lcd.rest.controller.{KeywordsController, ResourcesApp, LcdSwagger, UsersController}
import org.scalatra._
import javax.servlet.ServletContext
import org.slf4j.LoggerFactory
import scala.slick.session.Database

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new LcdSwagger

  val logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource
  logger.info("Created c3p0 connection pool")

  override def init(context: ServletContext) {
    val db = Database.forDataSource(cpds)
    context.mount(new UsersController, "/users/*")
//    context.mount(new KeywordsController, "/keywords/*")
    context mount(new ResourcesApp, "/api-docs/*")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDbConnection
  }

  private def closeDbConnection() {
    logger.info("closing c3p0 connection pool")
    cpds.close
  }
}
