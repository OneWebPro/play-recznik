package shared

case class AddPolish(parent: Long, word: String)

case class AddSerbian(parent: Long, word: String)

case class WordRespond(id: Long, word: String, added: Boolean = true)