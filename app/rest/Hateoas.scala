package rest

import play.api.libs.json.{JsValue, Json}

/**
 * Created by thiago on 3/28/15.
 */
case class Hateoas(page: Page, content: Array[JsValue])

object Hateoas {
  implicit val reads = Json.reads[Hateoas]
  implicit val writes = Json.writes[Hateoas]
}