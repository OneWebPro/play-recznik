package service

import database.{ServiceError, ErrorService}
import shared._
import dao._
import tables._

/**
 * @author loki
 */
/**
 *
 */
object TranslationService extends ErrorService {

  /**
   * Return all polish words started with @letter
   * @param letter Polish letter request
   * @return
   */
  def getPolishByFirst(letter: PolishFirstLetter): Either[ServiceError, List[PolishWord]] = withError {
    implicit session =>
      val l = letter.letter.charAt(0).toString.toLowerCase
      PolishWordTable.findByLetter(l).list
  }

  /**
   * Return all serbian words started with @letter
   * @param letter Serbian letter request
   * @return
   */
  def getSerbianByFirst(letter: SerbianFirstLetter): Either[ServiceError, List[SerbianWord]] = withError {
    implicit session =>
      val l = letter.letter.charAt(0).toString.toLowerCase
      SerbianWordTable.findByLetter(l).list
  }

  /**
   * Return serbian translations for @search object
   * @param search Polish search request
   * @return
   */
  def translatePolish(search: FindPolishTranslation): Either[ServiceError, List[SerbianWord]] = withError {
    implicit session =>
      val polish: PolishWord = {
        search.id match {
          case id: Long => PolishWordTable.findById(id).filter(_.active == true) match {
            case word: Option[PolishWord] => word.get
            case _ => throw new Exception("") //TODO
          }
          case _ => PolishWordTable.findByWord(search.word).firstOption match {
            case word: PolishWord => word
            case _ => throw new Exception("") //TODO
          }
        }
      }
      val translations: List[SerbianWord] = WordToWordTable.findSerbianTranslations(polish.id.get).list
      if (translations.isEmpty) {
        case _ => throw new Exception("") //TODO
      }
      translations
  }

  /**
   * Return polish translations for @search object
   * @param search Serbian search request
   * @return
   */
  def translateSerbian(search: FindSerbianTranslation): Either[ServiceError, List[PolishWord]] = withError {
    implicit session =>
      val serbian: SerbianWord = {
        search.id match {
          case id: Long => SerbianWordTable.findById(id).filter(_.active == true) match {
            case word: Option[SerbianWord] => word.get
            case _ => throw new Exception("") //TODO
          }
          case _ => SerbianWordTable.findByWord(search.word).firstOption match {
            case word: SerbianWord => word
            case _ => throw new Exception("") //TODO
          }
        }
      }
      val translations: List[PolishWord] = WordToWordTable.findPolishTranslations(serbian.id.get).list
      if (translations.isEmpty) {
        case _ => throw new Exception("") //TODO
      }
      translations
  }
}
