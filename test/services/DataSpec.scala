package test.services

import org.specs2.mutable._


/**
  * @author loki
  */
class DataSpec extends Specification with GlobalDatabaseTests{

   import dao._
   import scala.slick.jdbc.meta.MTable


   "Test data" should {
     "be initialized" in {
       runSession {
         implicit session =>
           import play.api._
           import play.api.Play.current
           Play.isTest mustEqual true
           MTable.getTables().list().size mustNotEqual 0
           WordToWordTable.findAll().size mustNotEqual 0
           PolishWordTable.findAll().size mustNotEqual 0
           SerbianWordTable.findAll().size mustNotEqual 0
       }
     }
   }

 }
