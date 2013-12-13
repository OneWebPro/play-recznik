package controllers

import play.api._
import play.api.mvc._
import jsmessages.api.JsMessages
import play.api.Play.current

object Application extends MainController {

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
}