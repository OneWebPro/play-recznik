package tables

import org.joda.time.LocalDate
import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.JodaSupport._
import org.joda.time.format.DateTimeFormat
import database._

/**
 * @author loki
 */

trait Word[T <: Entity[T]] extends Entity[T] {

  val id: Option[Long]

  val word: String

  val added: Boolean = false

  val active: Boolean = false
}


case class PolishWord(override val id: Option[Long], override val word: String, override val added: Boolean = false, override val active: Boolean = false) extends Word[PolishWord] {
  def withId(id: Long): PolishWord = copy(id = Some(id))
}


case class SerbianWord(override val id: Option[Long], override val word: String, override val added: Boolean = false, override val active: Boolean = false) extends Word[SerbianWord] {
  def withId(id: Long): SerbianWord = copy(id = Some(id))
}

abstract class WordTable[T <: Word[T]](tableName: String) extends Mapper[T](tableName) {
  def word = column[String]("word")
  def added = column[Boolean]("added")
}

trait PolishWordComponent {

  val PolishWordTable: PolishWordTable

  class PolishWordTable extends WordTable[PolishWord]("polish") {
    def * = id.? ~ word ~ added ~ active <>(PolishWord, PolishWord.unapply _)
  }

}

trait SerbianWordComponent {

  val SerbianWordTable: SerbianWordTable

  class SerbianWordTable extends WordTable[SerbianWord]("serbian") {
    def * = id.? ~ word ~ added ~ active <>(SerbianWord, SerbianWord.unapply _)
  }

}

case class WordToWord(id: Option[Long], serbian: Long, polish: Long, active: Boolean = false) extends Entity[WordToWord] {
  def withId(id: Long): WordToWord = copy(id = Some(id))
}

trait WordToWordComponent {
  self: SerbianWordComponent with PolishWordComponent =>

  val WordToWordTable: WordToWordTable

  class WordToWordTable extends Mapper[WordToWord]("word_word") {

    def serbian_id = column[Long]("word_serbian")

    def polish_id = column[Long]("word_polish")

    def * = id.? ~ serbian_id ~ polish_id ~ active <>(WordToWord, WordToWord.unapply _)

    def serbian_fk = foreignKey("serbian_fk", serbian_id, SerbianWordTable)(s => s.id)

    def polish_fk = foreignKey("polish_fk", polish_id, PolishWordTable)(p => p.id)

  }

}

