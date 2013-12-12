package data

import scala.xml._
import java.io.File
import dao._
import tables._
import play.api._
import play.api.Play.current

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
    val loadPath: String = {
      if (Play.isDev || Play.isProd) {
        path
      } else {
        "./test.xml"
      }
    }
    if (new File(loadPath).exists() && !new File(data).exists()) {
      val data: Elem = XML.load(new java.io.InputStreamReader(new java.io.FileInputStream(loadPath), "UTF-8"))
      val elements: Seq[(List[String], List[String])] = for (d <- data \\ "root" \\ "SRBPOL") yield {
        val polish: List[String] = (d \\ "P").text.replace("(", "[").replace(")", "]").split(", ").toList
        val serbian: List[String] = (d \\ "S").text.replace("(", "[").replace(")", "]").split(", ").toList
        (polish, serbian)
      }
      loadDb(elements)
    }
  }

  /**
   * Method change list of string to objects and set it to database.
   * We don't use insertAll or something like this because we need to keep
   * same structure.
   * @param elements words from xml
   */
  private def loadDb(elements: Seq[(List[String], List[String])])(implicit session: scala.slick.session.Session) {
    val daoElements = elements.map(element => {
      (
        element._1.map(polish => {
          PolishWordTable.findByWord(polish).firstOption match {
            case word: PolishWord => word
            case _ => {
              PolishWordTable.insert(
                PolishWord(None, polish.charAt(0).toString.toLowerCase, polish, false, true)
              )
            }
          }
        }),
        element._2.map(serbian => {
          SerbianWordTable.findByWord(serbian).firstOption match {
            case word: SerbianWord => word
            case _ => {
              SerbianWordTable.insert(
                SerbianWord(None, serbian.charAt(0).toString.toLowerCase, serbian, false, true)
              )
            }
          }
        }
        ))
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
}
