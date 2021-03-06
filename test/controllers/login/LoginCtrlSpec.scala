package controllers.login

import helpers.{RequestHelpers, Sessions}
import play.api.libs.crypto.{CSRFTokenSigner, CSRFTokenSignerProvider, CookieSignerProvider}
import play.api.libs.json.Json
import play.api.test.{FakeRequest, PlaySpecification}
import play.filters.csrf.{CSRF, CSRFAddToken}

class LoginCtrlSpec extends PlaySpecification with Sessions with RequestHelpers{


  "Login Controller" should {

    "login a user" in {

      val url = controllers.login.routes.LoginCtrl.login().url

      val request = route(application, FakeRequest(POST,url,CSRFHeader,Json.obj("username"->"abc","password"-> "ce")).withCookies(CSRFCookie)).get

      status(request) must equalTo(200)

    }

    "logout a user" in {

      val url = controllers.login.routes.LoginCtrl.logout().url

      val request = route(application, FakeRequest(GET,url).withSession(session:_ *)).get
      val requestUnauthorized = route(application, FakeRequest(GET,url).withSession(unauthorized:_ *)).get

      status(request) must equalTo(303)

    }
  }

}
