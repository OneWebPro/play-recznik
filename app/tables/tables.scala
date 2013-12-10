package tables

import org.joda.time.LocalDate
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JodaSupport._
import org.joda.time.format.DateTimeFormat
import database._

/**
 * @author loki
 */

case class Dictionary(id: Option[Long], first_polish: String, first_serbian: String, polish: String, serbian: String, added: Boolean = false, active: Boolean = false) extends Entity[Dictionary] {
  def withId(id: Long): Dictionary = copy(id = Some(id))
}

trait DictionaryComponent {

  val DictionaryTable: DictionaryTable

  class DictionaryTable extends Mapper[Dictionary]("dictionary") {
    def first_polish = column[String]("first_polish")

    def first_serbian = column[String]("first_serbian")

    def polish = column[String]("polish")

    def serbian = column[String]("serbian")

    def added = column[Boolean]("added")

    def * = id.? ~ first_polish ~ first_serbian ~ polish ~ serbian ~ added ~ active <>(Dictionary, Dictionary.unapply _)
  }

}

