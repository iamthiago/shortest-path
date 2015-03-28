package rest

import play.api.libs.json.Json

/**
 * Created by thiago on 3/28/15.
 */
case class Page (size: Int, totalElements: Int, totalPages: Int) {
  def this(size: Int, totalElements: Int) = {
    this(size, totalElements, if(totalElements % size == 0) totalElements / size else (totalElements / size) + 1)
  }
}

object Page {
  implicit val reads = Json.reads[Page]
  implicit val writes = Json.writes[Page]
}
