package service

import database.{ServiceError, ErrorService}
import shared._
import dao._
import tables._

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
        case None => {
          val wordToWord = WordToWordTable.insert(WordToWord(None, serbian.id.get, polish.id.get, active = true))
          WordRespond(polish, serbian, wordToWord)
        }
        case _ => throw new Error("") //TODO
      }

  }

  /**
   * Method to get polish translation from database or add it
   * @param translation Polish translation case class
   * @return
   */
  private def getPolishWord(translation: PolishTranslation)(implicit session: scala.slick.session.Session): PolishWord = {
    translation.id match {
      case id: Option[Long] => {
        PolishWordTable.findById(id.get).filter(_.active == true) match {
          case word: Option[PolishWord] => word.get
          case _ => throw new Exception("") //TODO
        }
      }
      case _ => {
        PolishWordTable.findByWord(translation.word).firstOption match {
          case word: Option[PolishWord] => word.get
          case _ => {
            if (!translation.word.isEmpty) {
              PolishWordTable.insert(PolishWord(
                None,
                translation.word.charAt(0).toString.toLowerCase,
                translation.word,
                added = true,
                active = true
              ))
            } else {
              throw new Exception("") //TODO
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
      case id: Option[Long] => {
        SerbianWordTable.findById(id.get).filter(_.active == true) match {
          case word: Option[SerbianWord] => word.get
          case _ => throw new Exception("") //TODO
        }
      }
      case _ => {
        SerbianWordTable.findByWord(translation.word).firstOption match {
          case word: Option[SerbianWord] => word.get
          case _ => {
            if (!translation.word.isEmpty) {
              SerbianWordTable.insert(SerbianWord(
                None,
                translation.word.charAt(0).toString.toLowerCase,
                translation.word,
                added = true,
                active = true
              ))
            } else {
              throw new Exception("") //TODO
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
        case id: Option[Long] => PolishWordTable.findById(id.get).filter(_.added == true).filter(_.active == true) match {
          case word: Option[PolishWord] => {
            if (!translation.word.isEmpty) {
              PolishWordTable.update(word.get.copy(
                first_letter = translation.word.charAt(0).toString.toLowerCase,
                word = translation.word
              ))
            } else {
              throw new Exception("") //TODO
            }
          }
          case _ => throw new Exception("") //TODO
        }
        case _ => throw new Exception("") //TODO
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
        case id: Option[Long] => SerbianWordTable.findById(id.get).filter(_.added == true).filter(_.active == true) match {
          case word: Option[SerbianWord] => {
            if (!translation.word.isEmpty) {
              SerbianWordTable.update(word.get.copy(
                first_letter = translation.word.charAt(0).toString.toLowerCase,
                word = translation.word
              ))
            } else {
              throw new Exception("") //TODO
            }
          }
          case _ => throw new Exception("") //TODO
        }
        case _ => throw new Exception("") //TODO
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
        case word: Option[PolishWord] => PolishWordTable.update(word.get.copy(active = false))
        case _ => throw new Exception("") //TODO
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
        case word: Option[SerbianWord] => SerbianWordTable.update(word.get.copy(active = false))
        case _ => throw new Exception("") //TODO
      }
  }

}
