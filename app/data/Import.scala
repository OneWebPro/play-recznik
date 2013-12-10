package data

import scala.xml._
import java.io.File
import dao._
import tables._

/**
 * @author loki
 */
/**
 * Import xml data to database
 */
object Import {
  /**
   * Method read data from database
   * @param path path of translations xml
   */
  def importData(path: String)(implicit session: scala.slick.session.Session) {
    if (new File(path).exists()) {
      val data: Elem = XML.load(new java.io.InputStreamReader(new java.io.FileInputStream(path), "UTF-8"))
      val elements: Seq[(List[String], List[String])] = for (d <- data \\ "root" \\ "SRBPOL") yield {
        val polish: List[String] = (d \\ "P").text.replace("(", "[").replace(")", "]").split(", ").toList
        val serbian: List[String] = (d \\ "S").text.replace("(", "[").replace(")", "]").split(", ").toList
        (polish, serbian)
      }
      loadDb(elements)
    }
  }

  /**
   * Method change list of string to objects and set it to database
   * @param elements words from xml
   */
  def loadDb(elements: Seq[(List[String], List[String])])(implicit session: scala.slick.session.Session) {
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
}
