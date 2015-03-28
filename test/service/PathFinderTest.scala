package service

import dto.{LogisticNetwork, Route}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.WithApplication

import scala.collection.JavaConversions._

/**
 * Created by thiago on 3/26/15.
 */
@RunWith(classOf[JUnitRunner])
class PathFinderTest extends Specification with PathService {

  "A Relationship" should {
    "be created" in new WithApplication {
      val a = neoService.createNode("A", "SP")
      val b = neoService.createNode("B", "SP")
      val c = neoService.createNode("C", "SP")
      val d = neoService.createNode("D", "SP")
      val e = neoService.createNode("E", "SP")

      neoService.createRelationship(a, b, 10)
      neoService.createRelationship(b, d, 15)
      neoService.createRelationship(a, c, 20)
      neoService.createRelationship(c, d, 30)
      neoService.createRelationship(b, e, 50)
      neoService.createRelationship(d, e, 30)

      val path = neoService.getShortestPath(a, d)
      println(path.weight())
      val list = path.nodes().toList
      list.foreach(n => println(neoService.getNodeProperty(n)))
    }
  }

  "A Node" should {
    "exist" in new WithApplication {
      neoService.createLogisticNetwork(LogisticNetwork("SP", List(Route("A", "B", 10))))
      neoService.createLogisticNetwork(LogisticNetwork("SP", List(Route("B", "D", 15))))
    }
  }
}