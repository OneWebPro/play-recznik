package tests.services


import org.specs2.mutable._
import service.WordsService
import play.api.db.slick.Config.driver.simple._


/**
 * @author loki
 */
class WordsServiceSpec extends Specification with GlobalDatabaseTests {

  import shared._
  import dao._

  "WordsService.addTranslation" should {
    "add translation with new words" in {
      runSession {
        implicit session =>
          val polishWord: String = "x"
          val serbianWord: String = "x2"
          val translations = WordToWordTable.findAll()
          val polish = PolishWordTable.findAll()
          val serbian = SerbianWordTable.findAll()
          WordsService.addTranslation(Translation(
            PolishTranslation(None, polishWord),
            SerbianTranslation(None, serbianWord)
          )).isLeft mustEqual false
          WordToWordTable.findAll().size mustEqual (translations.size + 1)
          PolishWordTable.findAll().size mustEqual (polish.size + 1)
          SerbianWordTable.findAll().size mustEqual (serbian.size + 1)
      }
    }
  }

  "WordsService.addTranslation" should {
    "add translation to polish exists word" in {
      runSession {
        implicit session =>
          val serbianWord: String = "x2"
          val default = PolishWordTable.findByLetter("Afryka".toLowerCase).first()
          val translations = WordToWordTable.findAll()
          val serbian = SerbianWordTable.findAll()
          WordsService.addTranslation(Translation(
            PolishTranslation(default.id, ""),
            SerbianTranslation(None, serbianWord)
          )).isLeft mustEqual false
          WordToWordTable.findAll().size mustEqual (translations.size + 1)
          SerbianWordTable.findAll().size mustEqual (serbian.size + 1)
      }
    }
  }

  "WordsService.addTranslation" should {
    "add translation to serbian exists word" in {
      runSession {
        implicit session =>
          val polishWord: String = "x2"
          val default = SerbianWordTable.findByLetter("Afrika".toLowerCase).first()
          val translations = WordToWordTable.findAll()
          val polish = PolishWordTable.findAll()
          WordsService.addTranslation(Translation(
            PolishTranslation(None,polishWord),
            SerbianTranslation(default.id, "")
          )).isLeft mustEqual false
          WordToWordTable.findAll().size mustEqual (translations.size + 1)
          PolishWordTable.findAll().size mustEqual (polish.size + 1)
      }
    }
  }

  "WordsService.editPolishTranslation" should {
    "edit polish translation but only added by user" in {
      runSession {
        implicit session =>
          val polishWord: String = "x"
          val serbianWord: String = "x2"
          val translations = WordsService.addTranslation(Translation(
            PolishTranslation(None, polishWord),
            SerbianTranslation(None, serbianWord)
          )).right.get
          WordsService.editPolishTranslation(PolishTranslation(translations.polish.id, "zz")).isLeft mustEqual false
          val changedTranslation = PolishWordTable.findByIdActive(translations.polish.id.get).get
          changedTranslation.word mustEqual "zz"
          val default = PolishWordTable.findByLetter("Afryka".toLowerCase).first()
          WordsService.editPolishTranslation(PolishTranslation(default.id, "zz")).isLeft mustEqual true
      }
    }
  }

  "WordsService.editSerbianTranslation" should {
    "edit serbian translation but only added by user" in {
      runSession {
        implicit session =>
          val polishWord: String = "x"
          val serbianWord: String = "x2"
          val translations = WordsService.addTranslation(Translation(
            PolishTranslation(None, polishWord),
            SerbianTranslation(None, serbianWord)
          )).right.get
          WordsService.editSerbianTranslation(SerbianTranslation(translations.serbian.id, "zz")).isLeft mustEqual false
          val changedTranslation = SerbianWordTable.findByIdActive(translations.serbian.id.get).get
          changedTranslation.word mustEqual "zz"
          val default = SerbianWordTable.findByLetter("Afrika".toLowerCase).first()
          WordsService.editSerbianTranslation(SerbianTranslation(default.id, "zz")).isLeft mustEqual true
      }
    }
  }

  "WordsService.removePolishTranslation" should {
    "remove polish translation but only added by user" in {
      runSession {
        implicit session =>
          val polishWord: String = "x"
          val serbianWord: String = "x2"
          val translations = WordsService.addTranslation(Translation(
            PolishTranslation(None, polishWord),
            SerbianTranslation(None, serbianWord)
          )).right.get
          WordsService.removePolishTranslation(RemovePolishTranslation(translations.polish.id.get)).isLeft mustEqual false
          val changedValue = PolishWordTable.findByIdActive(translations.polish.id.get)
          changedValue.isDefined mustNotEqual true
          val default = PolishWordTable.findByLetter("Afryka".toLowerCase).first()
          WordsService.removePolishTranslation(RemovePolishTranslation(default.id.get)).isLeft mustEqual true
      }
    }
  }

  "WordsService.removeSerbianTranslation" should {
    "remove serbian translation but only added by user" in {
      runSession {
        implicit session =>
          val polishWord: String = "x"
          val serbianWord: String = "x2"
          val translations = WordsService.addTranslation(Translation(
            PolishTranslation(None, polishWord),
            SerbianTranslation(None, serbianWord)
          )).right.get
          WordsService.removeSerbianTranslation(RemoveSerbianTranslation(translations.serbian.id.get)).isLeft mustEqual false
          val changedValue = SerbianWordTable.findByIdActive(translations.serbian.id.get)
          changedValue.isDefined mustNotEqual true
          val default = SerbianWordTable.findByLetter("Afrika".toLowerCase).first()
          WordsService.removeSerbianTranslation(RemoveSerbianTranslation(default.id.get)).isLeft mustEqual true
      }
    }
  }

}
