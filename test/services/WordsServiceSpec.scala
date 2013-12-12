package test.services


import org.specs2.mutable._


/**
 * @author loki
 */
class WordsServiceSpec extends Specification with GlobalDatabaseTests{

  import shared._
  import tables._
  import dao._
  import scala.slick.jdbc.meta.MTable


  "Test data" should {
    "be initialized" in {
      runSession {
        implicit session =>
          MTable.getTables().list().size mustNotEqual 0
          WordToWordTable.findAll().size mustNotEqual 0
          PolishWordTable.findAll().size mustNotEqual 0
          SerbianWordTable.findAll().size mustNotEqual 0
      }
    }
  }

}
