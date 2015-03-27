package service

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.WithApplication

/**
 * Created by thiago on 3/26/15.
 */
@RunWith(classOf[JUnitRunner])
class PathFinderTest extends Specification with PathService {

  "A Relationship" should {
    "be created" in new WithApplication {
      val a = neoService.createNode("A")
      val b = neoService.createNode("B")
      val c = neoService.createNode("C")
      val d = neoService.createNode("D")
      val e = neoService.createNode("E")

      neoService.createRelationship(a, b, 10)
      neoService.createRelationship(b, d, 15)
      neoService.createRelationship(a, c, 20)
      neoService.createRelationship(c, d, 30)
      neoService.createRelationship(b, e, 50)
      neoService.createRelationship(d, e, 30)

      val weightedPath = neoService.getShortestPath(a, d)

      println(weightedPath.weight())
    }
  }
}