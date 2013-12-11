import _root_.data.Import
import play.api._
import play.api.Play.current
import scala.slick.jdbc.meta.MTable
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._

/**
 * @author loki
 */
object Global extends GlobalSettings{

  /**
   * Database structure
   */
  lazy val ddl = dao.DDL.ddl

  /**
   * If database is not exists we create new.
   * @param app play.api.Application
   */
  override def onStart(app: Application) {
    DB.withSession {
      implicit s: scala.slick.session.Session => {
        if (MTable.getTables().list().isEmpty) {
          ddl.create
          Import.importData("./translations.xml")
        }else{
          if (dao.WordToWordTable.findAll().isEmpty) {
            Import.importData("./translations.xml")
          }
        }
      }
    }
  }

  /**
   * We remove database when we are stopping application.
   * @param app play.api.Application
   */
  override def onStop(app: Application) {
    DB.withSession {
      implicit s: scala.slick.session.Session =>
        if (!MTable.getTables().list().isEmpty && !Play.isDev) {
          ddl.drop
        }
    }
  }

}
