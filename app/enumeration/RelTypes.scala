package enumeration

import org.neo4j.graphdb.RelationshipType

/**
 * Created by thiago on 3/26/15.
 *
 * According to Neo4J documentation, create a enum class to work with their [[RelationshipType]] is a best practice.
 * Scala does not allow you to extend the [[RelationshipType]], so it is a best practice to create a implicit convert to work for you.
 *
 */
object RelTypes extends Enumeration {
  type RelTypes = Value
  val KNOWS = Value

  implicit def convert(relTypes: RelTypes) = new RelationshipType() {def name = relTypes.toString}
}
