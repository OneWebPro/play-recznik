package service

import database.{ServiceError, ErrorService}
import shared._
import dao._
import tables._
import play.api.i18n.Messages

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
      PolishWordTable.findByLetter(ListService.getSort(letter.letter.toLowerCase)).list
  }

  /**
   * Return all serbian words started with @letter
   * @param letter Serbian letter request
   * @return
   */
  def getSerbianByFirst(letter: SerbianFirstLetter): Either[ServiceError, List[SerbianWord]] = withError {
    implicit session =>
      SerbianWordTable.findByLetter(ListService.getSort(letter.letter.toLowerCase)).list
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
          case Some(id: Long) => PolishWordTable.findById(id).filter(_.active == true) match {
            case Some(word: PolishWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFound",id))
          }
          case None => PolishWordTable.findByWord(search.word.toLowerCase).firstOption match {
            case Some(word: PolishWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFoundByWord",search.word))
          }
        }
      }
      val translations: List[SerbianWord] = WordToWordTable.findSerbianTranslations(polish.id.get).list
      if (translations.isEmpty) {
        throw new Exception(Messages("service.error.emptyTranslations"))
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
          case Some(id: Long) => SerbianWordTable.findById(id).filter(_.active == true) match {
            case Some(word: SerbianWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFound",id))
          }
          case None => SerbianWordTable.findByWord(search.word.toLowerCase).firstOption match {
            case Some(word: SerbianWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFoundByWord",search.word))
          }
        }
      }
      val translations: List[PolishWord] = WordToWordTable.findPolishTranslations(serbian.id.get).list
      if (translations.isEmpty) {
        throw new Exception(Messages("service.error.emptyTranslations"))
      }
      translations
  }
}
