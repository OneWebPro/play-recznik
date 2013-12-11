package shared

import tables.{WordToWord, PolishWord, SerbianWord}

/* # Word service  # */

case class PolishTranslation(id: Option[Long], word: String)

case class SerbianTranslation(id: Option[Long], word: String)

case class Translation(polish: PolishTranslation, serbian: SerbianTranslation)

case class WordRespond(polish: PolishWord, serbian: SerbianWord, relation: WordToWord)

case class RemovePolishTranslation(id: Long)

case class RemoveSerbianTranslation(id: Long)

/* \#  Word service  # */

/* # Translation service  # */

case class PolishFirstLetter(letter: String)

case class SerbianFirstLetter(letter: String)

case class FindPolishTranslation(id: Option[Long], word: String)

case class FindSerbianTranslation(id: Option[Long], word: String)

/* \# Translation service  # */

/* # List service  # */

case class SortPolishList(page: Int, size: Int, find: String = "%")

case class SortSerbianList(page: Int, size: Int, find: String = "%")

case class Page(page: Int, size: Int, find: String)

/* \# List service  # */
