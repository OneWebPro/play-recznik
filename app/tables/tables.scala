package tables

import play.api.db.slick.Config.driver.simple._
import pl.onewebpro.database._

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

  def deactivate: PolishWord = copy(active = false)
}


case class SerbianWord(override val id: Option[Long], override val word: String, override val added: Boolean = false, override val active: Boolean = false) extends Word[SerbianWord] {
  def withId(id: Long): SerbianWord = copy(id = Some(id))

  def deactivate: SerbianWord = copy(active = false)
}

abstract class WordTable[T <: Word[T]](tag: Tag, tableName: String) extends Mapper[T](tag, tableName) {
  def word = column[String]("word")

  def added = column[Boolean]("added")
}

trait PolishWordComponent {

  class PolishWordTable(tag: Tag) extends WordTable[PolishWord](tag, "polish") {
    def * = (id.?, word, added, active) <>(PolishWord.tupled, PolishWord.unapply)
  }

}

trait SerbianWordComponent {

  class SerbianWordTable(tag: Tag) extends WordTable[SerbianWord](tag, "serbian") {
    def * = (id.?, word, added, active) <>(SerbianWord.tupled, SerbianWord.unapply)
  }

}

case class WordToWord(id: Option[Long], serbian: Long, polish: Long, active: Boolean = false) extends Entity[WordToWord] {
  def withId(id: Long): WordToWord = copy(id = Some(id))

  def deactivate: WordToWord = copy(active = false)
}

trait WordToWordComponent {
  self: SerbianWordComponent with PolishWordComponent =>

  lazy val SerbianWordTable = TableQuery[SerbianWordTable]

  lazy val PolishWordTable = TableQuery[PolishWordTable]

  class WordToWordTable(tag: Tag) extends Mapper[WordToWord](tag, "word_word") {

    def serbian_id = column[Long]("word_serbian")

    def polish_id = column[Long]("word_polish")

    def * = (id.?, serbian_id, polish_id, active) <>(WordToWord.tupled, WordToWord.unapply)

    def serbian_fk = foreignKey("serbian_fk", serbian_id, SerbianWordTable)(s => s.id)

    def polish_fk = foreignKey("polish_fk", polish_id, PolishWordTable)(p => p.id)

  }

}

