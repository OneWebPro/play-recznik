package services

import org.specs2.mutable._
import service.{TranslationService, ListService}
import dao.{SerbianWordTable, PolishWordTable}


/**
 * @author loki
 */
class ListServiceSpec extends Specification with GlobalDatabaseTests {

  import shared._

  val PAGE_SIZE: Int = 10

  "ListService.findPolish" should {
    "find all words" in {
      runSession {
        implicit session =>
          val letter: String = ""
          TranslationService.getPolishByFirst(PolishFirstLetter(letter)).isLeft mustEqual false
          val translations = PolishWordTable.findAll()
          val pages: Int = Math.round(translations.size / 10)
          val lastPage: Int = translations.size % 10
          val result = for (page <- 0 to pages) yield {
            !ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).isLeft &&
              ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.pages == pages &&
              ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.forall(word => word.word.indexOf(letter) == 0) && {
              if (page < pages) {
                ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.size == PAGE_SIZE
              } else {
                ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.size == lastPage
              }
            }
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "ListService.findPolish" should {
    "find words started with a" in {
      runSession {
        implicit session =>
          val letter: String = "a"
          TranslationService.getPolishByFirst(PolishFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getPolishByFirst(PolishFirstLetter(letter)).right.get
          val pages: Int = Math.round(translations.size / 10)
          val lastPage: Int = translations.size % 10
          val result = for (page <- 0 to pages) yield {
            !ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).isLeft &&
              ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.pages == pages &&
              ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.forall(word => word.word.indexOf(letter) == 0) && {
              if (page < pages) {
                ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.size == PAGE_SIZE
              } else {
                ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.size == lastPage
              }
            }
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "ListService.findPolish" should {
    "find words started with Al" in {
      runSession {
        implicit session =>
          val letter: String = "Al"
          TranslationService.getPolishByFirst(PolishFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getPolishByFirst(PolishFirstLetter(letter)).right.get
          val pages: Int = Math.round(translations.size / 10)
          val lastPage: Int = translations.size % 10
          val result = for (page <- 0 to pages) yield {
            !ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).isLeft &&
              ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.pages == pages &&
              ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.forall(word => word.word.indexOf(letter.toLowerCase) == 0) && {
              if (page < pages) {
                ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.size == PAGE_SIZE
              } else {
                ListService.findPolish(SortPolishList(page, PAGE_SIZE, letter)).right.get.elements.size == lastPage
              }
            }
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "ListService.findPolish" should {
    "find one word Afryka" in {
      runSession {
        implicit session =>
          val letter: String = "Afryka"
          ListService.findPolish(SortPolishList(0, PAGE_SIZE, letter)).isLeft mustEqual false
          val translations = ListService.findPolish(SortPolishList(0, PAGE_SIZE, letter)).right.get.elements.map(word => word.word)
          translations must containAllOf(List(letter.toLowerCase))
      }
    }
  }

  /* --- Serbian ---*/

  "ListService.findSerbian" should {
    "find all words" in {
      runSession {
        implicit session =>
          val letter: String = ""
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = SerbianWordTable.findAll()
          val pages: Int = Math.round(translations.size / 10)
          val lastPage: Int = translations.size % 10
          val result = for (page <- 0 to pages) yield {
            !ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).isLeft &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.pages == pages &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.forall(word => word.word.indexOf(letter) == 0) && {
              if (page < pages) {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == PAGE_SIZE
              } else {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == lastPage
              }
            }
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "ListService.findSerbian" should {
    "find words started with a" in {
      runSession {
        implicit session =>
          val letter: String = "a"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).right.get
          val pages: Int = Math.round(translations.size / 10)
          val lastPage: Int = translations.size % 10
          val result = for (page <- 0 to pages) yield {
            !ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).isLeft &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.pages == pages &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.forall(word => word.word.indexOf(letter) == 0) && {
              if (page < pages) {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == PAGE_SIZE
              } else {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == lastPage
              }
            }
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "ListService.findSerbian" should {
    "find words started with Al" in {
      runSession {
        implicit session =>
          val letter: String = "Al"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).right.get
          val pages: Int = Math.round(translations.size / 10)
          val lastPage: Int = translations.size % 10
          val result = for (page <- 0 to pages) yield {
            !ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).isLeft &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.pages == pages &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.forall(word => word.word.indexOf(letter.toLowerCase) == 0) && {
              if (page < pages) {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == PAGE_SIZE
              } else {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == lastPage
              }
            }
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "ListService.findSerbian" should {
    "find words started with č" in {
      runSession {
        implicit session =>
          val letter: String = "č"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).right.get
          val pages: Int = Math.round(translations.size / 10)
          val lastPage: Int = translations.size % 10
          val result = for (page <- 0 to pages) yield {
            !ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).isLeft &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.pages == pages &&
              ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.forall(word => word.word.charAt(0).toString.toLowerCase == letter.toLowerCase) && {
              if (page < pages) {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == PAGE_SIZE
              } else {
                ListService.findSerbian(SortSerbianList(page, PAGE_SIZE, letter)).right.get.elements.size == lastPage
              }
            }
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "ListService.findSerbian" should {
    "find one word Afrika" in {
      runSession {
        implicit session =>
          val letter: String = "Afrika"
          ListService.findSerbian(SortSerbianList(0, PAGE_SIZE, letter)).isLeft mustEqual false
          val translations = ListService.findSerbian(SortSerbianList(0, PAGE_SIZE, letter)).right.get.elements.map(word => word.word)
          translations must containAllOf(List(letter.toLowerCase))
      }
    }
  }

}
