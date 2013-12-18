package global

import org.joda.time.DateTime
import concurrent.ExecutionContext
import akka.util.Timeout
import concurrent.duration.`package`._
import play.api.mvc._
import play.libs.Akka
import secure.Secure
import akka.actor.ActorRef
import secure.SecureService
import database.ServiceError

/**
 * @author loki
 */

trait GlobalController extends Controller with Secure {

	/**
	 * Global database actor
	 */
	val globalActor: ActorRef

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

}


