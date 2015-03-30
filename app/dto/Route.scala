package dto

import play.api.libs.json.Json

/**
 * Created by thiago on 3/28/15.
 *
 * This is the basic Route dto where [[org.neo4j.graphdb.Relationship]] are created along with [[org.neo4j.graphdb.Node]]
 *
 */
case class Route(origin: String, destination: String, distance: Int)

object Route {
  implicit val reads = Json.reads[Route]
  implicit val writes = Json.writes[Route]
}