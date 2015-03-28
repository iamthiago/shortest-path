package dto

import play.api.libs.json.Json

/**
 * Created by thiago on 3/28/15.
 */
case class ShortestRequest(map: String, origin: String, destination: String, autonomy: Double, gasValue: Double)

object ShortestRequest {
  implicit val reads = Json.reads[ShortestRequest]
  implicit val writes = Json.writes[ShortestRequest]
}