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

  var path: String = "./translations.xml"

  var data: String = "./recznik.sql"

  var test: String = "./test.xml"

  /**
   * Method read data from database
   */
  def importData()(implicit session: scala.slick.session.Session) {
    val loadPath: String = {
      if (Play.isTest) {
        test
      } else {
        path
      }
    }
    if (new File(loadPath).exists() && (!new File(data).exists() || Play.isTest)) {
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
          PolishWordTable.findByWord(polish.toLowerCase).firstOption match {
            case Some(word: PolishWord) => word
            case None => {
              PolishWordTable.insert(
                PolishWord(None, polish.toLowerCase, added = false, active = true)
              )
            }
          }
        }),
        element._2.map(serbian => {
          SerbianWordTable.findByWord(serbian.toLowerCase).firstOption match {
            case Some(word: SerbianWord) => word
            case None => {
              SerbianWordTable.insert(
                SerbianWord(None, serbian.toLowerCase, added = false, active = true)
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
          WordToWordTable.insert(WordToWord(None, serbian.id.get, polish.id.get, active = true))
        })
      })
    })
  }
}
