package controllers

import play.api._
import play.api.mvc._
import jsmessages.api.JsMessages
import play.api.Play.current

object Application extends MainController {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  lazy val messages = new JsMessages

  lazy val jsMessages = Action { implicit request =>
    Ok(messages(Some("window.Messages"))).as(JAVASCRIPT)
  }

}