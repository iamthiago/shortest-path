package service

import dto.{LogisticNetwork, ShortestRequest, ShortestResult}
import service.module.GenericNeo4jServiceModule

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

/**
 * Created by thiago on 3/26/15.
 */
trait PathService extends GenericNeo4jServiceModule {
  object neoService extends GenericCommonNeo4jService {

    def createLogisticNetwork(logisticNetwork: LogisticNetwork): Future[Unit] = createNetwork(logisticNetwork)

    def getShortestPathWithCost(shortestRequest: ShortestRequest): Future[ShortestResult] = {
      val origin = isNodePresent(shortestRequest.origin, shortestRequest.map)
      val destination = isNodePresent(shortestRequest.destination, shortestRequest.map)

      require(origin.isDefined, "Map " + shortestRequest.map + " does not exist or does not contain the " + shortestRequest.origin + " node")
      require(destination.isDefined, "Map " + shortestRequest.map + " does not exist or does not contain the " + shortestRequest.destination + " node")

      val shortest = getShortestPath(origin.get, destination.get)
      val nodes = for(n <- shortest.nodes().toList) yield getNodeProperty(n)

      Future {
        ShortestResult(nodes, (shortest.weight() / shortestRequest.autonomy) * shortestRequest.gasValue)
      }
    }

    private def createNetwork(logisticNetwork: LogisticNetwork): Future[Unit] = {
      Future {
        for(n <- logisticNetwork.routes) {
          val x = isNodePresent(n.origin, logisticNetwork.map).getOrElse(createNode(n.origin, logisticNetwork.map))
          val y = isNodePresent(n.destination, logisticNetwork.map).getOrElse(createNode(n.destination, logisticNetwork.map))
          createRelationship(x, y, n.distance)
        }
      }
    }
  }
}

object PathService extends PathService