package controllers

import play.api.mvc._
import play.api.Play.current
import akka.actor.Props
import service.Database
import org.joda.time.DateTime
import scala.concurrent.ExecutionContext
import play.api.libs.concurrent.Akka
import akka.util.Timeout
import concurrent.duration.`package`._


/**
 * @author loki
 */

/**
 * Global controller configuration for default package controllers
 */
trait MainController extends Controller{

  /**
   * Time
   */
  implicit val time = () => new DateTime()

  /**
   * Actor context
   */
  implicit val executionContext: ExecutionContext = Akka.system.dispatcher

  /**
   * Actor timeout for request
   */
  implicit val timeout: Timeout = 10 seconds

	/**
	 * Global database actor
	 */
	lazy val globalActor = Akka.system.actorOf(Props[Database])

}
