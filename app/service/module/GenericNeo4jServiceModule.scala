package service.module

import config.Neo4jConfig
import org.neo4j.graphalgo.WeightedPath
import org.neo4j.graphdb.{Relationship, Node}

/**
 * Created by thiago on 3/26/15.
 *
 * This is the Generic Service Module, with no DI, using the Cake Pattern
 *
 */
trait GenericNeo4jServiceModule {

  def neoService: GenericCommonNeo4jService

  trait GenericCommonNeo4jService extends Neo4jConfig {
    def createNode(name: String): Node
    def createRelationship(from: Node, to: Node, distance: Int): Relationship
    def getShortestPath(from: Node, to: Node): WeightedPath
  }
}
