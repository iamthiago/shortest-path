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
 * This is the Generic Service Module, with no Dependency Injection but instead using the Cake Pattern design.
 *
 * This Generic class is basically an interface with default implementations.
 *
 */
trait GenericNeo4jServiceModule {

  def neoService: GenericCommonNeo4jService

  /**
   * This trait extends the Neo4J configuration enabling you to use the cool doTransaction wrap.
   */
  trait GenericCommonNeo4jService extends Neo4jConfig {

    /**
     *
     * Create a [[Node]]
     *
     * @param name The name of the [[Node]]
     * @param label The [[Label]] who will be used as the Network Map name eg SP, RJ.
     * @return The created [[Node]]
     */
    def createNode(name: String, label: String): Node = {
      var node: Node = null
      doTransaction(db => {
        node = db.createNode(DynamicLabel.label(label))
        node.setProperty("name", name)
      })
      node
    }

    /**
     *
     * Check if the giving name and label belongs to an existing [[Node]]
     *
     * @param name The name of the [[Node]] to be checked
     * @param label The label of the [[Node]] to be checked
     * @return A [[Node]] if it already exists, else a scala [[None]] is returned.
     */
    def isNodePresent(name: String, label: String): Option[Node] = {
      var node: Node = null
      doTransaction(db => {
        node = db.findNode(DynamicLabel.label(label), "name", name)
      })
      if(node != null) Some(node) else None
    }

    /**
     *
     * Read the property name from a [[Node]] inside a transaction.
     *
     * @param node The [[Node]] to return the property
     * @return The expected property name.
     */
    def getNodeProperty(node: Node) = {
      var property: String = null
      doTransaction(db => {
        property = node.getProperty("name").toString
      })
      property
    }

    /**
     *
     * Create the [[Relationship]] between two [[Node]] with a weight,
     * in this case a distance has been using.
     *
     * @param from The start point [[Node]]
     * @param to The destination [[Node]]
     * @param distance The weight that connects this relationship
     * @return A scala [[Future]]
     */
    def createRelationship(from: Node, to: Node, distance: Int): Future[Unit] = {
      Future {
        doTransaction(db => from.createRelationshipTo(to, KNOWS).setProperty("distance", distance))
      }
    }

    /**
     *
     * Using the Dijkstra algorithm, returns the shortest path between two [[Node]]
     *
     * @param from The start point [[Node]]
     * @param to The destination [[Node]]
     * @return A [[WeightedPath]] containing the total weight between the nodes.
     */
    def getShortestPath(from: Node, to: Node): WeightedPath = {
      var weightedPath: WeightedPath = null
      doTransaction(db => {
        weightedPath = GraphAlgoFactory.dijkstra(PathExpanders.forTypeAndDirection(KNOWS, Direction.BOTH), "distance").findSinglePath(from, to)
      })
      weightedPath
    }
  }
}
