package utils

import mockws.MockWS
import org.specs2.mock.Mockito
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.test.PlaySpecification
import play.api.http.HttpVerbs._
import play.api.libs.json.Json
import play.api.mvc.{Action, Results}


import scala.xml.NodeSeq

class WebServiceSpec extends PlaySpecification with Mockito{

  val ws = MockWS {
    case (GET, "/test") => Action { Results.Ok(<test value="Test">test</test>)}
    case (GET, "/fail") => Action { Results.BadRequest }
    case (POST, "/test") => Action { Results.Ok(<test value="Test">test</test>) }
    case (GET, "/abc") =>   Action { val x = 1/0; Results.Ok(x.toString) }
  }

  val service = new WebService(ws)

  "WebService" should {

    "wrap all request in a standardized manner" in {

      val converter: NodeSeq => String = (n: NodeSeq) => n.text

      val r1 = await(service.request("/test",GET)(converter))
      val r2 = await(service.request("/fail",GET)(converter))
      val r3 = await(service.request("/test",POST, Some(Json.obj("bac"-> "test")))(converter))
      val r4 = await(service.request("/abc", GET)(converter))

      r1.isRight must equalTo(true)
      r1.right.get must equalTo("test")

      r2.isLeft must equalTo(true)
      r2.left.get must equalTo("error.server.status.400")

      r3.isRight must equalTo(true)
      r3.right.get must equalTo("test")

      r4.isLeft must equalTo(true)
      r4.left.get must equalTo("error.server.unavailable")
    }

  }

}
