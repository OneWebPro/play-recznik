package json

import play.api.libs.json.{JsPath, Format, Json}
import shared._
import tables._
import play.api.libs.functional.syntax._
import tables.PolishWord
import tables.SerbianWord
import tables.PolishWord
import shared.SerbianTranslation
import tables.WordToWord
import tables.SerbianWord
import shared.PolishTranslation
import shared.RequestWord

/**
 * @author loki
 */
object JsonCodecs{

  implicit val wordFormat = Json.format[RequestWord]

  implicit val polishFormat = Json.format[PolishWord]

  implicit val serbianFormat = Json.format[SerbianWord]

  implicit val polishTranslationFormat = Json.format[PolishTranslation]

  implicit val serbianTranslationFormat = Json.format[SerbianTranslation]

  implicit val translationFormat: Format[shared.Translation] = (
    (JsPath \ "polish").format[PolishTranslation] and
      (JsPath \ "serbian").format[SerbianTranslation]
    )(shared.Translation.apply, unlift(shared.Translation.unapply))

  implicit val wordToWordFormat = Json.format[WordToWord]

  implicit val wordRespondFormat: Format[WordRespond] = (
    (JsPath \ "polish").format[PolishWord] and
      (JsPath \ "serbian").format[SerbianWord] and
      (JsPath \ "relation").format[WordToWord]
    )(WordRespond.apply, unlift(WordRespond.unapply))


}
