package shared

import tables.{WordToWord, PolishWord, SerbianWord}

case class PolishTranslation(id: Option[Long], word: String)

case class SerbianTranslation(id: Option[Long], word: String)

case class Translation(polish: PolishTranslation, serbian: SerbianTranslation)

case class WordRespond(polish:PolishWord,serbian:SerbianWord, relation:WordToWord)

case class RemovePolishTranslation(id: Long)

case class RemoveSerbianTranslation(id: Long)