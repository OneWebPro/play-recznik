package controllers

import play.api._
import play.api.mvc._
import akka.pattern.ask
import shared.{SortSerbianList, SerbianFirstLetter}
import database.ServiceError
import tables.{SerbianWord, PolishWord}
import play.api.libs.json._
import play.api.libs.functional.syntax._
/**
 * @author loki
 */
object Serbian extends MainController{

  implicit val serbianWord: Format[SerbianWord] = (
    (JsPath \ "id").formatNullable[Long] and
      (JsPath \ "word").format[String] and
      (JsPath \ "added").format[Boolean] and
      (JsPath \ "active").format[Boolean]
    )(SerbianWord.apply, unlift(SerbianWord.unapply))

  def find(word: String) = Action.async {
    globalActor.ask(SerbianFirstLetter(word)).mapTo[Either[ServiceError, List[SerbianWord]]].map({
      case Left(ko) => NotFound
      case Right(ok) => Ok(JsArray(ok.map(word => Json.toJson(word))))
    })
  }

  /*def sort(word: String) = Action.async {
    globalActor.ask(SortSerbianList(word)).mapTo[Either[ServiceError, List[SerbianWord]]].map({
      case Left(ko) => NotFound
      case Right(ok) => Ok(JsArray(ok.map(word => Json.toJson(word))))
    })
  }*/

}
