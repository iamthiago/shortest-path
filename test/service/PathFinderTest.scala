package service

import dto.{LogisticNetwork, Route, ShortestRequest, ShortestResult}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.WithApplication

import scala.util.{Failure, Success}

/**
 * Created by thiago on 3/26/15.
 */
@RunWith(classOf[JUnitRunner])
class PathFinderTest extends Specification with PathService {

  "A Backend Service" should {
    "create a logistic network" in new WithApplication {
      val future = neoService.createLogisticNetwork(LogisticNetwork("SP", List(Route("A", "B", 10))))
      future.onComplete {
        case Success(s) => s.isInstanceOf[Unit] must equalTo(true)
        case Failure(f) => throw new Exception(f.getMessage)
      }
    }

    "find the shortest path between nodes" in new WithApplication {
      val future = neoService.getShortestPathWithCost(ShortestRequest("SP", "A", "D", 10, 2.5))
      future.onComplete {
        case Success(s) => s.isInstanceOf[ShortestResult] must equalTo(true)
        case Failure(f) => throw new Exception(f.getMessage)
      }
    }
  }
}