package controllers

import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

/**
 * Created by thiago on 3/29/15.
 */
@RunWith(classOf[JUnitRunner])
class TestPathController extends Specification {

  "A Network" should {
    "create a logistic network" in new WithApplication {
      val request =
        route(FakeRequest(POST, "/network/create")
          .withJsonBody(
            Json.obj(
              "map" -> "SP",
              "routes" -> Json.arr(
                Json.obj(
                  "origin" -> "A",
                  "destination" -> "B",
                  "distance" -> 10
                ),
                Json.obj(
                  "origin" -> "B",
                  "destination" -> "D",
                  "distance" -> 15
                ),
                Json.obj(
                  "origin" -> "A",
                  "destination" -> "C",
                  "distance" -> 20
                ),
                Json.obj(
                  "origin" -> "C",
                  "destination" -> "D",
                  "distance" -> 30
                ),
                Json.obj(
                  "origin" -> "B",
                  "destination" -> "E",
                  "distance" -> 50
                ),
                Json.obj(
                  "origin" -> "D",
                  "destination" -> "E",
                  "distance" -> 30
                )
              )
            )
          )).get

      status(request) must equalTo(CREATED)
    }

    "find the shortest path between nodes" in new WithApplication {
      val request =
        route(FakeRequest(POST, "/network/shortest")
          .withJsonBody(
            Json.obj(
              "map" -> "SP",
              "origin" -> "A",
              "destination" -> "D",
              "autonomy" -> 10,
              "gasValue" -> 2.5
            )
          )).get

      status(request) must equalTo(OK)
      contentType(request) must beSome.which(_ == "application/json")
    }
  }
}
