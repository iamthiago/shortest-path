package json

import dto.ShortestResult
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json._
import play.api.test.WithApplication

/**
 * Created by thiago on 3/29/15.
 */
@RunWith(classOf[JUnitRunner])
class TestShortestResultJson extends Specification {

  "A Json" should {
    "be converted into Scala object" in new WithApplication {
      val json = Json.obj(
        "route" -> Json.arr("A", "B", "C"),
        "cost" -> 6.25
      )

      json.validate[ShortestResult] match {
        case s: JsSuccess[ShortestResult] => assert(true)
        case e: JsError => assert(false)
      }
    }
  }

  "A Scala class" should {
    "be converted into json" in new WithApplication {
      val classObject = ShortestResult(List("A", "B", "C"), 6.25)
      val json = Json.toJson(classObject)
      classObject mustEqual json.validate[ShortestResult].get
    }
  }
}
