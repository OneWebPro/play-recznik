package services

import shared._
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import play.api.db.slick.DB
import scala.slick.jdbc.meta.MTable
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import scala.slick.session.Session


trait GlobalDatabaseTests {

	def runSession[T](t: (Session) => T): T = {
		running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
			DB.withSession {
				implicit s: Session =>
					t(s)
			}
		}
	}

	def run[T](t: => T): T = {
		running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
			t
		}
	}

}
