package test.services


import org.specs2.mutable._


/**
 * @author loki
 */
class WordsServiceSpec extends Specification with GlobalDatabaseTests{

  import shared._
  import tables._
  import dao._

  "User" should {
    "be edited" in {
      runSession {
        implicit session =>
          true mustEqual true
      }
    }
  }

}
