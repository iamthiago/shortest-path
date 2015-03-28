package service.module

import _root_.config.Neo4jConfig
import enumeration.RelTypes._
import org.neo4j.graphalgo.{GraphAlgoFactory, WeightedPath}
import org.neo4j.graphdb._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

/**
 * Created by thiago on 3/26/15.
 *
 * This is the Generic Service Module, with no DI, using the Cake Pattern
 *
 */
trait GenericNeo4jServiceModule {

  def neoService: GenericCommonNeo4jService

  trait GenericCommonNeo4jService extends Neo4jConfig {
    def createNode(name: String, label: String): Node = {
      var node: Node = null
      doTransaction(db => {
        node = db.createNode(DynamicLabel.label(label))
        node.setProperty("name", name)
      })
      node
    }

    def isNodePresent(name: String, label: String): Option[Node] = {
      var node: Node = null
      doTransaction(db => {
        node = db.findNode(DynamicLabel.label(label), "name", name)
      })
      if(node != null) Some(node) else None
    }

    def getNodeProperty(node: Node) = {
      var property: String = null
      doTransaction(db => {
        property = node.getProperty("name").toString
      })
      property
    }

    def createRelationship(from: Node, to: Node, distance: Int): Future[Unit] = {
      Future {
        doTransaction(db => from.createRelationshipTo(to, KNOWS).setProperty("distance", distance))
      }
    }

    def getShortestPath(from: Node, to: Node): WeightedPath = {
      var weightedPath: WeightedPath = null
      doTransaction(db => {
        weightedPath = GraphAlgoFactory.dijkstra(PathExpanders.forTypeAndDirection(KNOWS, Direction.BOTH), "distance").findSinglePath(from, to)
      })
      weightedPath
    }
  }
}
