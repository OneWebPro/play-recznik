package test.services

import org.specs2.mutable._


/**
 * @author loki
 */
class TranslationServiceSpec extends Specification with GlobalDatabaseTests {

  import shared._
  import service.TranslationService


  "TranslationService.getPolishByFirst" should {
    "have words on letter a" in {
      runSession {
        implicit session =>
          val letter: String = "a"
          TranslationService.getPolishByFirst(PolishFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getPolishByFirst(PolishFirstLetter(letter)).right.get
          val result = for (translation <- translations) yield {
            translation.word.charAt(0).toString equals letter
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "TranslationService.getPolishByFirst" should {
    "don't have words on letter x" in {
      runSession {
        implicit session =>
          val letter: String = "x"
          TranslationService.getPolishByFirst(PolishFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getPolishByFirst(PolishFirstLetter(letter)).right.get
          translations.size mustEqual 0
      }
    }
  }

  "TranslationService.getPolishByFirst" should {
    "have words started with letters Al" in {
      runSession {
        implicit session =>
          val letter: String = "Al"
          TranslationService.getPolishByFirst(PolishFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getPolishByFirst(PolishFirstLetter(letter)).right.get
          val result = for (translation <- translations) yield{
            translation.word.contains(letter.toLowerCase) equals true
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "TranslationService.getPolishByFirst" should {
    "have word Afryka" in {
      runSession {
        implicit session =>
          val letter: String = "Afryka"
          TranslationService.getPolishByFirst(PolishFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getPolishByFirst(PolishFirstLetter(letter)).right.get
          translations.size mustEqual 1
          val result = for (translation <- translations) yield{
            translation.word equals letter.toLowerCase
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "TranslationService.translatePolish" should {
    "translate word Afryka to Afrika by word" in {
      runSession {
        implicit session =>
          val search: String = "Afryka"
          val find: String = "Afrika"
          TranslationService.translatePolish(FindPolishTranslation(None,search)).isLeft mustEqual false
          val translation = TranslationService.translatePolish(FindPolishTranslation(None,search)).right.get
          val translations = translation.map( word => word.word)
          translations must containAllOf(List(find.toLowerCase))
      }
    }
  }

  "TranslationService.translatePolish" should {
    "translate word Afryka to Afrika by id" in {
      runSession {
        implicit session =>
          val search: String = "Afryka"
          val find: String = "Afrika"
          TranslationService.getPolishByFirst(PolishFirstLetter(search)).isLeft mustEqual false
          val word = TranslationService.getPolishByFirst(PolishFirstLetter(search)).right.get
          TranslationService.translatePolish(FindPolishTranslation(word.head.id, "")).isLeft mustEqual false
          val translation = TranslationService.translatePolish(FindPolishTranslation(None,search)).right.get
          val translations = translation.map( word => word.word)
          translations must containAllOf(List(find.toLowerCase))
      }
    }
  }

  "TranslationService.translatePolish" should {
    "translate word estragon to (estrgon, kozlac) by word" in {
      runSession {
        implicit session =>
          val search: String = "estragon"
          val find: List[String] = List("estrgon", "kozlac")
          TranslationService.translatePolish(FindPolishTranslation(None,search)).isLeft mustEqual false
          val translation = TranslationService.translatePolish(FindPolishTranslation(None,search)).right.get
          val words: List[String] = translation.map( serbianWord => serbianWord.word)
          words must containAllOf(find)
      }
    }
  }

  "TranslationService.translatePolish" should {
    "translate word estragon to (estrgon, kozlac) by id" in {
      runSession {
        implicit session =>
          val search: String = "estragon"
          val find: List[String] = List("estrgon", "kozlac")
          TranslationService.getPolishByFirst(PolishFirstLetter(search)).isLeft mustEqual false
          val word = TranslationService.getPolishByFirst(PolishFirstLetter(search)).right.get
          TranslationService.translatePolish(FindPolishTranslation(word.head.id, "")).isLeft mustEqual false
          val translation = TranslationService.translatePolish(FindPolishTranslation(None,search)).right.get
          val words: List[String] = translation.map( serbianWord => serbianWord.word)
          words must containAllOf(find)
      }
    }
  }

  /* --- Serbian ---*/

  "TranslationService.getSerbianByFirst" should {
    "have words on letter a" in {
      runSession {
        implicit session =>
          val letter: String = "a"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).right.get
          val result = for (translation <- translations) yield {
            translation.word.charAt(0).toString equals letter
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "TranslationService.getSerbianByFirst" should {
    "don't have words on letter x" in {
      runSession {
        implicit session =>
          val letter: String = "x"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).right.get
          translations.size mustEqual 0
      }
    }
  }

  "TranslationService.getSerbianByFirst" should {
    "have words started with letters Al" in {
      runSession {
        implicit session =>
          val letter: String = "Al"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).right.get
          val result = for (translation <- translations) yield{
            translation.word.contains(letter.toLowerCase) equals true
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "TranslationService.getSerbianByFirst" should {
    "have word Afrika" in {
      runSession {
        implicit session =>
          val letter: String = "Afrika"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).isLeft mustEqual false
          val translations = TranslationService.getSerbianByFirst(SerbianFirstLetter(letter)).right.get
          translations.size mustEqual 1
          val result = for (translation <- translations) yield{
            translation.word equals letter.toLowerCase
          }
          result.forall(element => element) mustEqual true
      }
    }
  }

  "TranslationService.translateSerbian" should {
    "translate word Afrika to Afryka by word" in {
      runSession {
        implicit session =>
          val search: String = "Afrika"
          val find: String = "Afryka"
          TranslationService.translateSerbian(FindSerbianTranslation(None,search)).isLeft mustEqual false
          val translation = TranslationService.translateSerbian(FindSerbianTranslation(None,search)).right.get
          val translations = translation.map( word => word.word)
          translations must containAllOf(List(find.toLowerCase))
      }
    }
  }

  "TranslationService.translateSerbian" should {
    "translate word Afrika to Afryka by id" in {
      runSession {
        implicit session =>
          val search: String = "Afrika"
          val find: String = "Afryka"
          TranslationService.getSerbianByFirst(SerbianFirstLetter(search)).isLeft mustEqual false
          val word = TranslationService.getSerbianByFirst(SerbianFirstLetter(search)).right.get
          TranslationService.translateSerbian(FindSerbianTranslation(word.head.id, "")).isLeft mustEqual false
          val translation = TranslationService.translateSerbian(FindSerbianTranslation(None,search)).right.get
          val translations = translation.map( word => word.word)
          translations must containAllOf(List(find.toLowerCase))
      }
    }
  }

  "TranslationService.translateSerbian" should {
    "translate word kertriđž to (kałamarz, wkład do drukarki) by word" in {
      runSession {
        implicit session =>
          val search: String = "kertriđž"
          val find: List[String] = List("kałamarz", "wkład do drukarki")
          TranslationService.translateSerbian(FindSerbianTranslation(None,search)).isLeft mustEqual false
          val translation = TranslationService.translateSerbian(FindSerbianTranslation(None,search)).right.get
          val words: List[String] = translation.map( serbianWord => serbianWord.word)
          words must containAllOf(find)
      }
    }
  }

  "TranslationService.translateSerbian" should {
    "translate word kertriđž to (kałamarz, wkład do drukarki) by id" in {
      runSession {
        implicit session =>
          val search: String = "kertriđž"
          val find: List[String] = List("kałamarz", "wkład do drukarki")
          TranslationService.getSerbianByFirst(SerbianFirstLetter(search)).isLeft mustEqual false
          val word = TranslationService.getSerbianByFirst(SerbianFirstLetter(search)).right.get
          TranslationService.translateSerbian(FindSerbianTranslation(word.head.id, "")).isLeft mustEqual false
          val translation = TranslationService.translateSerbian(FindSerbianTranslation(None,search)).right.get
          val words: List[String] = translation.map( serbianWord => serbianWord.word)
          words must containAllOf(find)
      }
    }
  }

}
