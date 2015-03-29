package dto

import play.api.libs.json.Json

/**
 * Created by thiago on 3/28/15.
 *
 * This dto represents the basic entry point of the system,
 * creating the Logistic Network with their [[org.neo4j.graphdb.Node]] and [[org.neo4j.graphdb.Relationship]]
 *
 */
case class LogisticNetwork(map: String, routes: List[Route])

object LogisticNetwork {
  implicit val reads = Json.reads[LogisticNetwork]
  implicit val writes = Json.writes[LogisticNetwork]
}