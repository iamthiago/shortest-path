package dto

import play.api.libs.json.Json

/**
 * Created by thiago on 3/28/15.
 *
 * The [[ShortestRequest]] represents the entry point for calculation the Shortest Path based on Dijkstra algorithm
 *
 */
case class ShortestRequest(map: String, origin: String, destination: String, autonomy: Double, gasValue: Double)

object ShortestRequest {
  implicit val reads = Json.reads[ShortestRequest]
  implicit val writes = Json.writes[ShortestRequest]
}