package service

import akka.actor._
import akka.pattern.pipe
import java.util.concurrent.Executors
import akka.util.Timeout
import concurrent.{Future, ExecutionContext}
import concurrent.duration.`package`._

/**
 * @author loki
 */

/**
 * Actor implementation fo async services
 */
class Database extends Actor with ActorLogging {

	implicit val timeout: Timeout = 5 second
	implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4))

	def receive = {
		case _ => {}
	}

}
