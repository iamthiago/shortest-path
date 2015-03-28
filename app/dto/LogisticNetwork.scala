package dto

import play.api.libs.json.Json

/**
 * Created by thiago on 3/28/15.
 */
case class LogisticNetwork(map: String, routes: List[Route])

object LogisticNetwork {
  implicit val reads = Json.reads[LogisticNetwork]
  implicit val writes = Json.writes[LogisticNetwork]
}