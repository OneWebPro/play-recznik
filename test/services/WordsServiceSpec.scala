package test.services


import org.specs2.mutable._


/**
 * @author loki
 */
class WordsServiceSpec extends Specification with GlobalDatabaseTests {

  import shared._
  import tables._
  import dao._

  "WordsService.addTranslation" should {
    "add translation with one new words" in {
      runSession {
        implicit session =>
      }
    }
  }

  "WordsService.addTranslation" should {
    "add translation with many new words" in {
      runSession {
        implicit session =>
      }
    }
  }

  "WordsService.addTranslation" should {
    "add translation to exists word" in {
      runSession {
        implicit session =>
      }
    }
  }

  "WordsService.addTranslation" should {
    "add many translations to exists word" in {
      runSession {
        implicit session =>
      }
    }
  }

  "WordsService.editPolishTranslation" should {
    "edit polish translation but only added by user" in {
      runSession {
        implicit session =>
      }
    }
  }

  "WordsService.editSerbianTranslation" should {
    "edit serbian translation but only added by user" in {
      runSession {
        implicit session =>
      }
    }
  }

  "WordsService.removePolishTranslation" should {
    "remove polish translation but only added by user" in {
      runSession {
        implicit session =>
      }
    }
  }

  "WordsService.removeSerbianTranslation" should {
    "remove serbian translation but only added by user" in {
      runSession {
        implicit session =>
      }
    }
  }

}
