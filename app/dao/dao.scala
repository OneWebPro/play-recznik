package dao

import tables._
import play.api.db.slick.Config.driver.simple._
import pl.onewebpro.database._
import scala.slick.jdbc.JdbcBackend.Session

/**
 * @author loki
 */

private[dao] trait DaoStructure extends
PolishWordComponent with
SerbianWordComponent with
WordToWordComponent {
  override lazy val PolishWordTable = TableQuery[PolishWordTable]
  override lazy val SerbianWordTable = TableQuery[SerbianWordTable]
  lazy val WordToWordTable = TableQuery[WordToWordTable]
}

/**
 * Object contains all tables structures to create or remove database.
 */
object DDL extends DaoStructure {
  val ddl = PolishWordTable.ddl ++
    SerbianWordTable.ddl ++
    WordToWordTable.ddl
}

private[dao] trait DAO[Element <: Entity[Element]] extends DatabaseDAO[Element] with DaoStructure

object PolishWordTable extends DAO[tables.PolishWord] {

  val self = PolishWordTable.baseTableRow

  /**
   * Searching polish translations using word comparison
   */
  val findByWord = for {
    param <- Parameters[String]
    word <- PolishWordTable if word.word === param && word.active === true
  } yield word

  /**
   * Searching polish translations using word like comparison
   */
  val findByLetter = for {
    letter <- Parameters[String]
    word <- PolishWordTable if (word.word like letter) && word.active === true
  } yield word

  /**
   * Searching polish translations using word like comparison and paging it
   * @return
   */
  def pagePolishList(page: shared.Page)(implicit s: Session): List[PolishWord] = (for {
    word <- PolishWordTable if (word.word like page.find) && word.active === true
  } yield word).drop(page.size * page.page).take(page.size).list
}

object SerbianWordTable extends DAO[tables.SerbianWord] {
  val self = SerbianWordTable.baseTableRow

  /**
   * Searching serbian translations using word comparison
   */
  val findByWord = for {
    param <- Parameters[String]
    word <- SerbianWordTable if word.word === param && word.active === true
  } yield word

  /**
   * Searching serbian translations using word like comparison
   */
  val findByLetter = for {
    letter <- Parameters[String]
    word <- SerbianWordTable if (word.word like letter) && word.active === true
  } yield word

  /**
   * Searching serbian translations using word like comparison and paging it
   * @return
   */
  def pageSerbianList(page: shared.Page)(implicit s: Session): List[SerbianWord] = (for {
    word <- SerbianWordTable if (word.word like page.find) && word.active === true
  } yield word).drop(page.size * page.page).take(page.size).list


}

object WordToWordTable extends DAO[tables.WordToWord] {
  val self = WordToWordTable.baseTableRow

  /**
   * Search words relations
   */
  val findByParents = for {
    (polish, serbian) <- Parameters[(Long, Long)]
    wordToWord <- WordToWordTable if wordToWord.polish_id === polish && wordToWord.serbian_id === serbian && wordToWord.active === true
  } yield wordToWord

  /**
   * Search serbian translations for polish word
   */
  val findSerbianTranslations = for {
    polish <- Parameters[Long]
    wordToWord <- WordToWordTable if wordToWord.polish_id === polish
    serbian <- SerbianWordTable if serbian.id === wordToWord.serbian_id && serbian.active === true
  } yield serbian

  /**
   * Search polish translation for serbian word
   */
  val findPolishTranslations = for {
    serbian <- Parameters[Long]
    wordToWord <- WordToWordTable if wordToWord.serbian_id === serbian
    polish <- PolishWordTable if polish.id === wordToWord.polish_id && polish.active === true
  } yield polish
}

