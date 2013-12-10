package controllers

import play.api.mvc._
import play.api.Play.current
import flash.FlashWrapper._
import play.api.i18n.Messages
import global.GlobalController
import akka.actor.Props
import play.api.libs.concurrent.Akka
import service.Database
import database.ServiceError


/**
 * @author loki
 */

/**
 * Global controller configuration for default package controllers
 */
trait MainController extends GlobalController {


	type UserType = None.type

	/**
	 * User request to get user from database or cache
	 * @return
	 */
	def getUserInfo: (RequestHeader) => Either[ServiceError, MainController#UserType] = info

  /**
   * Cheat for compilation issue
   * @param request play.api.mvc.RequestHeader
   * @return
   */
  def info(request: RequestHeader): Either[ServiceError, UserType] = {
    Right(None)
  }

	/**
	 * Global database actor
	 */
	lazy val globalActor = Akka.system.actorOf(Props[Database])

	/**
	 * Redirect to index and show flash message
	 * @param request play.api.mvc.RequestHeader
	 * @return
	 */
	def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.index).flashing(
		rw(request).danger(Messages("error.notLogged"))
	)

	/**
	 * Redirect to index and show flash message
	 * @param request play.api.mvc.RequestHeader
	 * @return
	 */
	def notOnUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.index).flashing(
		rw(request).danger(Messages("error.logged"))
	)

	/**
	 * Redirect to index and show flash message
	 * @param request play.api.mvc.RequestHeader
	 * @return
	 */
	def notPermissions(request: RequestHeader) = Results.Redirect(routes.Application.index).flashing(
		rw(request).danger(Messages("error.permissions"))
	)

	/**
	 * Bad request action
	 * @param request play.api.mvc.RequestHeader
	 * @return
	 */
	def badRequest(request: RequestHeader): SimpleResult = Results.Redirect(routes.Application.index).flashing(
		rw(request).danger(Messages("error.permissions"))
	)
}
