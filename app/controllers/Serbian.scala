package controllers

import play.api._
import play.api.mvc._
import akka.pattern.ask
import shared._
import play.api.libs.json._
import scala.concurrent.Future
import tables.PolishWord
import shared.SortSerbianList
import play.api.libs.json.JsArray
import tables.SerbianWord
import shared.RequestWord
import shared.FindSerbianTranslation
import pl.onewebpro.database.ServiceError
import shared.SerbianFirstLetter
import json.JsonCodecs._

/**
 * @author loki
 */
object Serbian extends MainController {

  def find = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      json.validate[RequestWord].fold(
        valid = {
          form =>
            globalActor.ask(SerbianFirstLetter(form.word.getOrElse(""))).mapTo[Either[ServiceError, List[SerbianWord]]].map({
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
            globalActor.ask(FindSerbianTranslation(form.id, form.word.getOrElse(""))).mapTo[Either[ServiceError,  (List[PolishWord], SerbianWord)]].map({
              case Left(ko) => NotFound(ko.error)
              case Right(ok) => Ok(
                Json.obj(
                  "word" -> Json.toJson(ok._2),
                  "list" -> JsArray(ok._1.map(word => Json.toJson(word)))
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


  def sort(page: Int, size: Int) = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      json.validate[RequestWord].fold(
        valid = {
          form =>
            globalActor.ask(SortSerbianList(page, size, form.word.getOrElse("%"))).mapTo[Either[ServiceError, ResultPage[SerbianWord]]].map({
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
              SerbianTranslation(form.id, form.word.getOrElse(""))
            ).mapTo[Either[ServiceError, SerbianWord]].map({
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
      globalActor.ask(RemoveSerbianTranslation(id)).mapTo[Either[ServiceError, SerbianWord]].map({
        case Left(ko) => NotFound(ko.error)
        case Right(ok) => Ok(Json.toJson(ok))
      })
  }

}
