package controllers

import play.api._
import play.api.mvc._
import jsmessages.api.JsMessages
import play.api.Play.current
import play.api.libs.json._
import shared.{WordRespond, SerbianTranslation, PolishTranslation}
import scala.concurrent.Future
import akka.pattern.ask
import database.ServiceError
import tables.{WordToWord, SerbianWord, PolishWord}
import play.api.libs.functional.syntax._

object Application extends MainController {

  implicit val polishTranslationFormat = Json.format[PolishTranslation]
  implicit val serbianTranslationFormat = Json.format[SerbianTranslation]
  implicit val translationFormat: Format[shared.Translation] = (
    (JsPath \ "polish").format[PolishTranslation] and
      (JsPath \ "serbian").format[SerbianTranslation]
    )(shared.Translation.apply, unlift(shared.Translation.unapply))
  implicit val polishFormat = Json.format[PolishWord]
  implicit val serbianFormat = Json.format[SerbianWord]
  implicit val wordToWordFormat = Json.format[WordToWord]
  implicit val wordRespondFormat: Format[WordRespond] = (
    (JsPath \ "polish").format[PolishWord] and
      (JsPath \ "serbian").format[SerbianWord] and
      (JsPath \ "relation").format[WordToWord]
    )(WordRespond.apply, unlift(WordRespond.unapply))

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /**
   * Method show name
   * @param name Name of view ex. body or directives/tabs
   * @return
   */
  def view(name: String) = Action {
    val file: java.io.File = Play.getFile("public/partials/" + name + ".html")
    if (file.exists()) {
      Ok(scala.io.Source.fromFile(file).mkString).as("text/html; charset=utf-8")
    } else {
      NotFound
    }
  }

  lazy val messages = new JsMessages

  lazy val jsMessages = Action { implicit request =>
    Ok(messages(Some("window.Messages"))).as(JAVASCRIPT)
  }

  def addTranslation = Action.async(parse.json) {
    implicit request =>
      val json = request.body
      json.validate[shared.Translation].fold(
        valid = {
          form =>
            globalActor.ask(form).mapTo[Either[ServiceError, WordRespond]].map({
              case Left(ko) => NotFound(ko.error)
              case Right(ok) => Ok(Json.toJson(ok))
            })
        },
        invalid = {
          error =>
            Future.successful(NotFound)
        }
      )
  }
}