package json

import dto.{LogisticNetwork, Route}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json._
import play.api.test.WithApplication

/**
 * Created by thiago on 3/29/15.
 */
@RunWith(classOf[JUnitRunner])
class TestLogisticNetworkJson extends Specification {

  "A Json" should {
    "be converted into Scala object" in new WithApplication {
      val json = Json.obj(
        "map" -> "SP",
        "routes" -> Json.arr(
          Json.obj(
            "origin" -> "A",
            "destination" -> "B",
            "distance" -> 10
          )
        )
      )

      json.validate[LogisticNetwork] match {
        case s: JsSuccess[LogisticNetwork] => assert(true)
        case e: JsError => assert(false)
      }
    }
  }

  "A Scala class" should {
    "be converted into json" in new WithApplication {
      val classObject = LogisticNetwork("SP", List(Route("A", "B", 10)))
      val json = Json.toJson(classObject)
      classObject mustEqual json.validate[LogisticNetwork].get
    }
  }
}
