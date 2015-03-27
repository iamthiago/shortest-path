package service

import enumeration.RelTypes._
import org.neo4j.graphalgo.{GraphAlgoFactory, WeightedPath}
import org.neo4j.graphdb.{Direction, Node, PathExpanders, Relationship}
import service.module.GenericNeo4jServiceModule

/**
 * Created by thiago on 3/26/15.
 */
trait PathService extends GenericNeo4jServiceModule {

  object neoService extends GenericCommonNeo4jService {

    override def createNode(name: String): Node = {
      var node: Node = null
      doTransaction(db => {
        node = db.createNode()
        node.setProperty("name", name)
      })
      node
    }

    override def createRelationship(from: Node, to: Node, distance: Int): Relationship = {
      var relationship: Relationship = null
      doTransaction(db => {
        relationship = from.createRelationshipTo(to, KNOWS)
        relationship.setProperty("distance", distance)
        relationship
      })
      relationship
    }

    override def getShortestPath(from: Node, to: Node): WeightedPath = {
      var weightedPath: WeightedPath = null
      doTransaction(db => {
        weightedPath = GraphAlgoFactory.dijkstra(PathExpanders.forTypeAndDirection(KNOWS, Direction.BOTH), "distance").findSinglePath(from, to)
      })
      weightedPath
    }
  }
}

object PathService extends PathService