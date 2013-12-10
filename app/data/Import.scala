package data

import scala.xml._
import java.io.File

/**
 * @author loki
 */
/**
 * Import xml data to database
 */
object Import {
  def importData(path: String) {
    if (new File(path).exists()) {
      val data: Elem = XML.load(new java.io.InputStreamReader(new java.io.FileInputStream(path), "UTF-8"))
      for (d <- data \\ "root" \\ "SRBPOL") {
        val polish = (d \\ "P").text.replace("(", "[").replace(")", "]")
        val serbian = (d \\ "S").text.replace("(", "[").replace(")", "]")
      }
    }
  }
}
