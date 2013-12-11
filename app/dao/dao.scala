package dao
import tables._
import play.api.db.slick.Config.driver.simple._
import org.joda.time.LocalDate
import com.github.tototoshi.slick.JodaSupport._
import database.{Mapper, Entity}

/**
 * @author loki
 */

private[dao] trait DaoStructure extends
PolishWordComponent with
SerbianWordComponent with
WordToWordComponent {
  val PolishWordTable = new PolishWordTable
  val SerbianWordTable = new SerbianWordTable
  val WordToWordTable = new WordToWordTable
}


/**
 * DAO trait is trait that help implements all default methods from database.Mapper
 */
private[dao] trait DAO extends DaoStructure {

  /**
   * Type element of DAO
   */
  type Element <: Entity[Element]

  /**
   * Element of DAO
   */
  val self: Mapper[Element]

  /**
   * Insert entity element to database and return it. If element hase id defined nothing will happen.
   * @return
   */
  def insert(element: Element)(implicit session: Session): Element = self.insert(element)

  /**
   * Method update entity if hase id
   * @return
   */
  def update(element: Element)(implicit session: Session): Element = self.update(element)

  /**
   * Update & Insert. If hase defined id it will updated if not it will be inserted.
   * @return
   */
  def upinsert(element: Element)(implicit session: Session): Element = self.upinsert(element)

  /**
   * Searching element using id field. Return Option element
   * @param id Long
   * @return
   */
  def findById(id: Long)(implicit session: Session): Option[Element] = self.findById(id)

  /**
   * Use findAllQuery. Default is searching if filed approved is true
   * @param active Boolean
   * @return
   */
  def findAll(active: Boolean = true)(implicit session: Session): List[Element] = self.findAll(active)
}

/**
 * Object contains all tables structures to create or remove database.
 */
object DDL extends DaoStructure {
  val ddl = PolishWordTable.ddl ++
            SerbianWordTable.ddl ++
            WordToWordTable.ddl
}

object PolishWordTable extends DAO {
  type Element = tables.PolishWord
  val self = PolishWordTable

  val findByWord = for{
    param <- Parameters[String]
    word <- PolishWordTable if word.word === param
  } yield word

}

object SerbianWordTable extends DAO {
  type Element = tables.SerbianWord
  val self = SerbianWordTable

  val findByWord = for{
    param <- Parameters[String]
    word <- SerbianWordTable if word.word === param
  } yield word
}

object WordToWordTable extends DAO {
  type Element = tables.WordToWord
  val self = WordToWordTable
}

