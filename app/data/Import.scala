package data

import scala.xml._
import java.io.File
import dao._
import tables._
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Q.interpolation

/**
 * @author loki
 */
/**
 * Import xml data to database
 */
object Import {

  var data: String = "./recznik.sql"

  /**
   * Method read data from database
   * @param path path of translations xml
   */
  def importData(path: String)(implicit session: scala.slick.session.Session) {
    if (new File(path).exists() && !new File(data).exists()) {
      val data: Elem = XML.load(new java.io.InputStreamReader(new java.io.FileInputStream(path), "UTF-8"))
      val elements: Seq[(List[String], List[String])] = for (d <- data \\ "root" \\ "SRBPOL") yield {
        val polish: List[String] = (d \\ "P").text.replace("(", "[").replace(")", "]").split(", ").toList
        val serbian: List[String] = (d \\ "S").text.replace("(", "[").replace(")", "]").split(", ").toList
        (polish, serbian)
      }
      loadDb(elements)
    }
    if(new File(data).exists()){
      importSql()
    }
  }

  /**
   * Method change list of string to objects and set it to database
   * @param elements words from xml
   */
  private def loadDb(elements: Seq[(List[String], List[String])])(implicit session: scala.slick.session.Session) {
    val daoElements = elements.map(element => {
      (
        element._1.map(polish => PolishWordTable.insert(
          PolishWord(None, polish.charAt(0).toString.toLowerCase, polish, false, true)
        )),
        element._2.map(serbian => SerbianWordTable.insert(
          SerbianWord(None, serbian.charAt(0).toString.toLowerCase, serbian, false, true)
        ))
        )
    })
    //Nightmare construction ]:->
    daoElements.foreach(element => {
      element._2.foreach(serbian => {
        element._1.foreach(polish => {
          WordToWordTable.insert(WordToWord(None, serbian.id.get, polish.id.get, true))
        })
      })
    })
  }

  private def importSql()(implicit session: scala.slick.session.Session) {
    val source = scala.io.Source.fromFile(data)
    val sql = source.getLines().mkString("")
    source.close()
    Q.updateNA("SET GLOBAL max_allowed_packet=" + 2 * 1024 * 1024).execute()
    Q.updateNA(sql).execute()
  }
}
