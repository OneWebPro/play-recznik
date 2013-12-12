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

    /* --- WORD SERVICE --- */

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

    /* --- TRANSLATION SERVICE --- */

    // # --- Find polish words start with letter
    case params: PolishFirstLetter => Future {
      TranslationService.getPolishByFirst(params)
    } pipeTo sender

    // # --- Find serbian words start with letter
    case params: SerbianFirstLetter => Future {
      TranslationService.getSerbianByFirst(params)
    } pipeTo sender

    // # --- Find serbian translations for polish words
    case params: FindPolishTranslation => Future {
      TranslationService.translatePolish(params)
    } pipeTo sender

    // # --- Find polish translations for serbian words
    case params: FindSerbianTranslation => Future {
      TranslationService.translateSerbian(params)
    } pipeTo sender

    /* --- LIST SERVICE --- */

    // # --- Find polish translations sorted by word
    case params: SortPolishList => Future {
      ListService.findPolish(params)
    } pipeTo sender

    // # --- Find serbian translations sorted by word
    case params: SortSerbianList => Future {
      ListService.findSerbian(params)
    } pipeTo sender

    case _ => {}
  }

}
