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
object WordsService extends ErrorService{

  def addPolishWord(word : AddPolish): Either[ServiceError, WordRespond] = withError {
    implicit session =>
    PolishWordTable.findByWord(word.word).firstOption match {
      case None => {
        val added = PolishWordTable.insert(PolishWord(
          None,
          word.word.charAt(0).toString.toLowerCase,
          word.word,
          true,
          true
        ))
        WordRespond()
      }
      case _ => {
        throw new Exception("")
      }
    }

  }

  def addSerbianWord(word : AddSerbian): Either[ServiceError, WordRespond] = withError {
    implicit session =>

  }

}
