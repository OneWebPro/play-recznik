package tables

import org.joda.time.LocalDate
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JodaSupport._
import org.joda.time.format.DateTimeFormat
import database._

/**
 * @author loki
 */

case class PolishWord(id: Option[Long], first_letter: String, word: String, added: Boolean = false, active: Boolean = false) extends Entity[PolishWord] {
  def withId(id: Long): PolishWord = copy(id = Some(id))
}

trait PolishWordComponent {

  self: WordToWordComponent =>

  val PolishWordTable: PolishWordTable

  class PolishWordTable extends Mapper[PolishWord]("polish") {
    def first_letter = column[String]("first_letter")

    def word = column[String]("word")

    def added = column[Boolean]("added")

    def * = id.? ~ first_letter ~ word ~ added ~ active <>(PolishWord, PolishWord.unapply _)

    def serbian_words = WordToWordTable.filter(_.polish_id === id).flatMap(_.serbian_fk)
  }

}

case class SerbianWord(id: Option[Long], first_letter: String, word: String, added: Boolean = false, active: Boolean = false) extends Entity[SerbianWord] {
  def withId(id: Long): SerbianWord = copy(id = Some(id))
}

trait SerbianWordComponent {

  self: WordToWordComponent =>

  val SerbianWordTable: SerbianWordTable

  class SerbianWordTable extends Mapper[SerbianWord]("serbian") {
    def first_letter = column[String]("first_letter")

    def word = column[String]("word")

    def added = column[Boolean]("added")

    def * = id.? ~ first_letter ~ word ~ added ~ active <>(SerbianWord, SerbianWord.unapply _)

    def polish_words = WordToWordTable.filter(_.serbian_id === id).flatMap(_.polish_fk)

  }

}

case class WordToWord(id: Option[Long], polish: Long, serbian: Long, active: Boolean = false) extends Entity[WordToWord] {
  def withId(id: Long): WordToWord = copy(id = Some(id))
}

trait WordToWordComponent {
  self: SerbianWordComponent with PolishWordComponent =>

  val WordToWordTable: WordToWordTable

  class WordToWordTable extends Mapper[WordToWord]("word_word") {
    def polish_id = column[Long]("word_polish")

    def serbian_id = column[Long]("word_serbian")

    def * = id.? ~ polish_id ~ serbian_id ~ active <>(WordToWord, WordToWord.unapply _)

    def polish_fk = foreignKey("polish_fk", polish_id, PolishWordTable)(p => p.id)

    def serbian_fk = foreignKey("serbian_fk", serbian_id, SerbianWordTable)(s => s.id)
  }

}

