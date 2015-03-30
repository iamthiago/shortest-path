package service

import dto.{LogisticNetwork, ShortestRequest, ShortestResult}
import service.module.GenericNeo4jServiceModule

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

/**
 * Created by thiago on 3/26/15.
 *
 * This is the main class used behind the Rest service to manage the Logistic Network.
 * It connects with Neo4J through the default interface with default implemented methods.
 *
 */
trait PathService extends GenericNeo4jServiceModule {
  object neoService extends GenericCommonNeo4jService {

    /**
     *
     * Creates the [[LogisticNetwork]]
     *
     * @param logisticNetwork The [[LogisticNetwork]] receveid from the Rest to be created.
     * @return A scala [[Future]]
     */
    def createLogisticNetwork(logisticNetwork: LogisticNetwork): Future[Unit] = createNetwork(logisticNetwork)

    /**
     *
     * Returns the shortest path between two nodes also calculating the lower cost.
     *
     * @param shortestRequest The [[ShortestRequest]] with the necessary information for Dijkstra algorithm.
     * @return A scala [[Future]] of [[ShortestResult]]
     */
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

    /**
     *
     * A private method to create the [[LogisticNetwork]], checking if the given
     * nodes already exist or not. In case of yes, the [[org.neo4j.graphdb.Node]] will be reused, if not it will create a new one.
     *
     * @param logisticNetwork
     * @return
     */
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