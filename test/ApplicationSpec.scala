import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import services.GlobalDatabaseTests

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification with GlobalDatabaseTests{

  "Application" should {

    "send 404 on a bad request" in new WithApp {
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the index page" in new WithApp {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      val content = contentAsString(home)
      content must contain("rečnik | słownik")
      content must contain("ng-controller=\"SiteController\"")
      content must contain("<link rel=\"stylesheet\" href=\"/assets/stylesheets/main.css\">")
      content must contain("<script type=\"text/javascript\" src=\"/messages.js\"></script>")
      content must contain("<script type=\"text/javascript\" data-main=\"/assets/javascripts/main\" src=\"/assets/javascripts/require.js\"></script>")
    }
  }
}
