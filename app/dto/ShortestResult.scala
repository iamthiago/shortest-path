package dto

import play.api.libs.json.Json

/**
 * Created by thiago on 3/28/15.
 *
 * After asking for the Shortest Path, this dto representation will be returned as json format.
 */
case class ShortestResult(route: List[String], cost: Double)

object ShortestResult {
  implicit val reads = Json.reads[ShortestResult]
  implicit val writes = Json.writes[ShortestResult]
}