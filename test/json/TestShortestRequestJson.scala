package json

import dto.ShortestRequest
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json._
import play.api.test.WithApplication

/**
 * Created by thiago on 3/29/15.
 */
@RunWith(classOf[JUnitRunner])
class TestShortestRequestJson extends Specification {

  "A Json" should {
    "be converted into Scala object" in new WithApplication {
      val json = Json.obj(
        "map" -> "SP",
        "origin" -> "A",
        "destination" -> "D",
        "autonomy" -> 10,
        "gasValue" -> 2.5
      )

      json.validate[ShortestRequest] match {
        case s: JsSuccess[ShortestRequest] => assert(true)
        case e: JsError => assert(false)
      }
    }
  }

  "A Scala class" should {
    "be converted into json" in new WithApplication {
      val classObject = ShortestRequest("SP", "A", "B", 10, 2.5)
      val json = Json.toJson(classObject)
      classObject mustEqual json.validate[ShortestRequest].get
    }
  }
}
