package service

import akka.actor._
import akka.pattern.pipe
import java.util.concurrent.Executors
import akka.util.Timeout
import concurrent.{Future, ExecutionContext}
import concurrent.duration.`package`._
import shared._

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
    // # --- Add translation to database
    case params: Translation => Future {
      WordsService.addTranslation(params)
    } pipeTo sender
    // # --- Update polish translation
    case params: PolishTranslation => Future {
      WordsService.editPolishTranslation(params)
    } pipeTo sender
    // # --- Update serbian translation
    case params: SerbianTranslation => Future {
      WordsService.editSerbianTranslation(params)
    } pipeTo sender
    // # --- Remove polish translation
    case params: RemovePolishTranslation => Future {
      WordsService.removePolishTranslation(params)
    } pipeTo sender
    // # --- Remove serbian translation
    case params: RemoveSerbianTranslation => Future {
      WordsService.removeSerbianTranslation(params)
    } pipeTo sender
    case _ => {}
  }

}
