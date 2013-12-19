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

  val translateLetters: Map[String, String] = Map(
    "љ" -> "lj", "њ" -> "nj", "џ" -> "dž",
    "а" -> "a", "б" -> "b", "в" -> "v",
    "г" -> "g", "д" -> "d", "ђ" -> "đ",
    "е" -> "e", "ж" -> "ž", "з" -> "z",
    "и" -> "i", "ј" -> "j", "к" -> "k",
    "л" -> "l", "м" -> "m", "н" -> "n",
    "о" -> "o", "п" -> "p", "р" -> "r",
    "с" -> "s", "т" -> "t", "ћ" -> "ć",
    "у" -> "u", "ф" -> "f", "х" -> "h",
    "ц" -> "c", "ч" -> "č", "ш" -> "š"
  )

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
      SerbianWordTable.findByLetter(ListService.getSort(translate(letter.letter))).list
  }

  /**
   * Return serbian translations for @search object
   * @param search Polish search request
   * @return
   */
  def translatePolish(search: FindPolishTranslation): Either[ServiceError, (List[SerbianWord], PolishWord)] = withError {
    implicit session =>
      val polish: PolishWord = {
        search.id match {
          case Some(id: Long) => PolishWordTable.findById(id).filter(_.active == true) match {
            case Some(word: PolishWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFound",id))
          }
          case None => PolishWordTable.findByWord(search.word.toLowerCase).firstOption.filter(_.active == true) match {
            case Some(word: PolishWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFoundByWord",search.word))
          }
        }
      }
      val translations: List[SerbianWord] = WordToWordTable.findSerbianTranslations(polish.id.get).list
      if (translations.isEmpty) {
        throw new Exception(Messages("service.error.emptyTranslations"))
      }
      (translations, polish)
  }

  /**
   * Return polish translations for @search object
   * @param search Serbian search request
   * @return
   */
  def translateSerbian(search: FindSerbianTranslation): Either[ServiceError, (List[PolishWord], SerbianWord)] = withError {
    implicit session =>
      val serbian: SerbianWord = {
        search.id match {
          case Some(id: Long) => SerbianWordTable.findById(id).filter(_.active == true) match {
            case Some(word: SerbianWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFound",id))
          }
          case None => SerbianWordTable.findByWord(translate(search.word)).firstOption.filter(_.active == true) match {
            case Some(word: SerbianWord) => word
            case None => throw new Exception(Messages("service.error.wordNotFoundByWord",search.word))
          }
        }
      }
      val translations: List[PolishWord] = WordToWordTable.findPolishTranslations(serbian.id.get).list
      if (translations.isEmpty) {
        throw new Exception(Messages("service.error.emptyTranslations"))
      }
      (translations, serbian)
  }

  /**
   * Translate word from cyrillic to latin
   * @return
   */
  def translate(word: String): String = {
    translateLetters.foldLeft(word.toLowerCase) {
      case (w, (cyrillic, latin)) => w.replace(cyrillic.toLowerCase, latin.toLowerCase)
    }
  }

}
