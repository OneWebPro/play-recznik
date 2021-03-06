package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import scala.concurrent.Future
import akka.pattern.ask
import pl.onewebpro.database.ServiceError
import play.api.libs.json._
import json.JsonCodecs._
import shared.WordRespond
import service.TranslationService

object Application extends MainController {

  def index = Action {
    Ok(views.html.index())
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