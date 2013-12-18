import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import play.api.mvc._
import play.api.mvc.Results._
import play.mvc.Http.HeaderNames
import services.GlobalDatabaseTests
import play.api.libs.json._
import play.api.libs.functional.syntax._
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification with GlobalDatabaseTests{

  val restHeader = Some("application/json; charset=utf-8")

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

    "partials should be rendered" in new WithApp {
      val partialsList: List[String] = List(
        "add",
        "body",
        "letters",
        "lettersAffix",
        "login",
        "menu",
        "polish-body",
        "polish-list",
        "polish-translate",
        "serbian-body",
        "serbian-list",
        "serbian-translate"
      )
      for (partial <- partialsList) {
        val home = route(FakeRequest(GET, "/view/" + partial)).get
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
      }
    }

    "requests should be application/javascript" in new WithApp {
      val routes: Map[String, String] = Map(
        POST -> "/api/serbian/find",
        POST -> "/api/serbian/translate",
        POST -> "/api/serbian/save",
        POST -> "/api/serbian/sort/10/0",
        GET -> "/api/serbian/remove/0",
        POST -> "/api/polish/find",
        POST -> "/api/polish/translate",
        POST -> "/api/polish/save",
        POST -> "/api/polish/sort/10/0",
        GET -> "/api/polish/remove/0",
        POST -> "/api/add"
      )
      for ((method, uri) <- routes) {
        val request = route(FakeRequest(method, uri)).get
        status(request) must equalTo(BAD_REQUEST)
        contentAsString(request) must contain("[Expecting text/json or application/json body]")
      }
    }

    "serbian and polish list empty request" in new WithApp {
      val serbianList = route(FakeRequest(POST, "/api/serbian/find").withJsonBody(Json.obj())).get
      status(serbianList) must equalTo(OK)
      header(play.mvc.Http.HeaderNames.CONTENT_TYPE,serbianList) must equalTo(restHeader)
      val polishList = route(FakeRequest(POST, "/api/polish/find").withJsonBody(Json.obj())).get
      status(polishList) must equalTo(OK)
      header(play.mvc.Http.HeaderNames.CONTENT_TYPE,polishList) must equalTo(restHeader)
    }


    }
}
