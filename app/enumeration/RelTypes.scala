package enumeration

import org.neo4j.graphdb.RelationshipType

/**
 * Created by thiago on 3/26/15.
 */
object RelTypes extends Enumeration {
  type RelTypes = Value
  val KNOWS = Value

  implicit def convert(relTypes: RelTypes) = new RelationshipType() {def name = relTypes.toString}
}
