package service

import pl.onewebpro.database._
import shared._
import dao._
import tables._
import play.api.i18n.Messages

/**
 * @author loki
 */
/**
 * Service for polish
 */
object WordsService extends ErrorService {

  /**
   * Add translation to database
   * @param translation Object with polish and serbian translation
   * @return
   */
  def addTranslation(translation: Translation): Either[ServiceError, WordRespond] = withError {
    implicit session =>
      val polish: PolishWord = getPolishWord(translation.polish)
      val serbian: SerbianWord = getSerbianWord(translation.serbian)
      WordToWordTable.findByParents(polish.id.get, serbian.id.get).firstOption match {
        case Some(_) => throw new Exception(Messages("service.error.translationExists"))
        case None => {
          val wordToWord = WordToWordTable.insert(WordToWord(None, serbian.id.get, polish.id.get, active = true))
          WordRespond(polish, serbian, wordToWord)
        }
      }

  }

  /**
   * Method to get polish translation from database or add it
   * @param translation Polish translation case class
   * @return
   */
  private def getPolishWord(translation: PolishTranslation)(implicit session: scala.slick.session.Session): PolishWord = {
    translation.id match {
      case Some(id: Long) => {
        PolishWordTable.findById(id).filter(_.active == true) match {
          case Some(word: PolishWord) => word
          case None => throw new Exception(Messages("service.error.wordNotFound", id))
        }
      }
      case None => {
        PolishWordTable.findByWord(translation.word.toLowerCase).firstOption.filter(_.active == true) match {
          case Some(word: PolishWord) => word
          case None => {
            if (!translation.word.isEmpty) {
              PolishWordTable.insert(PolishWord(
                None,
                translation.word.toLowerCase,
                added = true,
                active = true
              ))
            } else {
              throw new Exception(Messages("service.error.emptyTranslation"))
            }
          }
        }
      }
    }
  }

  /**
   * Method to get serbian word from database or add it
   * @param translation Serbian translation case class
   * @return
   */
  private def getSerbianWord(translation: SerbianTranslation)(implicit session: scala.slick.session.Session): SerbianWord = {
    translation.id match {
      case Some(id: Long) => {
        SerbianWordTable.findById(id).filter(_.active == true) match {
          case Some(word: SerbianWord) => word
          case None => throw new Exception(Messages("service.error.wordNotFound", id))
        }
      }
      case None => {
        SerbianWordTable.findByWord(TranslationService.translate(translation.word)).firstOption.filter(_.active == true) match {
          case Some(word: SerbianWord) => word
          case None => {
            if (!translation.word.isEmpty) {
              SerbianWordTable.insert(SerbianWord(
                None,
                TranslationService.translate(translation.word),
                added = true,
                active = true
              ))
            } else {
              throw new Exception(Messages("service.error.emptyTranslation"))
            }
          }
        }
      }
    }
  }

  /**
   * Edit polish translation
   * @param translation Polish translation case class
   * @return
   */
  def editPolishTranslation(translation: PolishTranslation): Either[ServiceError, PolishWord] = withError {
    implicit session =>
      translation.id match {
        case Some(id: Long) => PolishWordTable.findById(id).filter(_.added == true).filter(_.active == true) match {
          case Some(word: PolishWord) => {
            if (!translation.word.isEmpty) {
              PolishWordTable.update(word.copy(word = translation.word.toLowerCase))
            } else {
              throw new Exception(Messages("service.error.emptyTranslation"))
            }
          }
          case None => throw new Exception(Messages("service.error.wordNotFound", translation.id))
        }
        case None => throw new Exception(Messages("service.error.notAvailable", translation.id))
      }
  }

  /**
   * Edit serbian translation
   * @param translation Serbian translation case class
   * @return
   */
  def editSerbianTranslation(translation: SerbianTranslation): Either[ServiceError, SerbianWord] = withError {
    implicit session =>
      translation.id match {
        case Some(id: Long) => SerbianWordTable.findById(id).filter(_.added == true).filter(_.active == true) match {
          case Some(word: SerbianWord) => {
            if (!translation.word.isEmpty) {
              SerbianWordTable.update(word.copy(word = TranslationService.translate(translation.word)))
            } else {
              throw new Exception(Messages("service.error.emptyTranslation"))
            }
          }
          case None => throw new Exception(Messages("service.error.wordNotFound", translation.id))
        }
        case None => throw new Exception(Messages("service.error.notAvailable", translation.id))
      }
  }

  /**
   * Remove polish translation from list of visible elements
   * @param element Polish remove request case class
   * @return
   */
  def removePolishTranslation(element: RemovePolishTranslation): Either[ServiceError, PolishWord] = withError {
    implicit session =>
      PolishWordTable.findById(element.id).filter(_.added == true).filter(_.active == true) match {
        case Some(word: PolishWord) => {
          val translations = WordToWordTable.findSerbianTranslations(word.id.get).list()
          for (translation <- translations) {
            WordToWordTable.update(WordToWordTable.findByParents(word.id.get, translation.id.get).first().copy(active = false))
          }
          PolishWordTable.update(word.copy(active = false))
        }
        case None => throw new Exception(Messages("service.error.notAvailable", element.id))
      }
  }

  /**
   * Remove serbian translation from list of visible elements
   * @param element Serbian remove request case class
   * @return
   */
  def removeSerbianTranslation(element: RemoveSerbianTranslation): Either[ServiceError, SerbianWord] = withError {
    implicit session =>
      SerbianWordTable.findById(element.id).filter(_.added == true).filter(_.active == true) match {
        case Some(word: SerbianWord) => {
          val translations = WordToWordTable.findPolishTranslations(word.id.get).list
          for (translation <- translations) {
            WordToWordTable.update(WordToWordTable.findByParents(translation.id.get,word.id.get).first().copy(active = false))
          }
          SerbianWordTable.update(word.copy(active = false))
        }
        case None => throw new Exception(Messages("service.error.notAvailable", element.id))
      }
  }

}
