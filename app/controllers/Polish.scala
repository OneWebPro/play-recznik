package controllers

import play.api._
import play.api.mvc._
import akka.pattern.ask
import shared._
import database.ServiceError
import tables.{SerbianWord, PolishWord}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.Future
import tables.PolishWord
import shared.PolishFirstLetter
import play.api.libs.json.JsArray
import tables.SerbianWord
import shared.RequestWord
import shared.FindPolishTranslation
import database.ServiceError
import shared.SortPolishList

/**
 * @author loki
 */
object Polish extends MainController {

  implicit val wordFormat = Json.format[RequestWord]
  implicit val polishFormat = Json.format[PolishWord]
  implicit val serbianFormat = Json.format[SerbianWord]

  def find = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      json.validate[RequestWord].fold(
        valid = {
          form =>
            globalActor.ask(PolishFirstLetter(form.word.getOrElse(""))).mapTo[Either[ServiceError, List[PolishWord]]].map({
              case Left(ko) => NotFound(ko.error)
              case Right(ok) => Ok(JsArray(ok.map(word => Json.toJson(word))))
            })
        },
        invalid = {
          errors =>
            Future.successful(NotFound)
        }
      )
  }

  def translate = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      json.validate[RequestWord].fold(
        valid = {
          form =>
            globalActor.ask(FindPolishTranslation(form.id, form.word.getOrElse(""))).mapTo[Either[ServiceError, List[SerbianWord]]].map({
              case Left(ko) => NotFound(ko.error)
              case Right(ok) => Ok(JsArray(ok.map(word => Json.toJson(word))))
            })
        },
        invalid = {
          errors =>
            Future.successful(NotFound)
        }
      )
  }

  def sort(page: Int, size: Int) = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      json.validate[RequestWord].fold(
        valid = {
          form =>
            globalActor.ask(SortPolishList(page, size, form.word.getOrElse("%"))).mapTo[Either[ServiceError, ResultPage[PolishWord]]].map({
              case Left(ko) => NotFound(ko.error)
              case Right(ok) => Ok(
                Json.obj(
                  "results" -> JsArray(ok.elements.map(word => Json.toJson(word))),
                  "page" -> ok.page,
                  "pages" -> ok.pages
                )
              )
            })
        },
        invalid = {
          errors =>
            Future.successful(NotFound)
        }
      )
  }

  def save = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      json.validate[RequestWord].fold(
        valid = {
          form =>
            globalActor.ask(
              PolishTranslation(form.id,form.word.getOrElse(""))
            ).mapTo[Either[ServiceError,PolishWord]].map({
              case Left(ko) => NotFound(ko.error)
              case Right(ok) => Ok(Json.toJson(ok))
            })
        },
        invalid = {
          errors =>
            Future.successful(NotFound)
        }
      )
  }

  def remove(id: Long) = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      globalActor.ask(RemovePolishTranslation(id)).mapTo[Either[ServiceError, PolishWord]].map({
        case Left(ko) => NotFound(ko.error)
        case Right(ok) => Ok(Json.toJson(ok))
      })
  }

}