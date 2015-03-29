package config

import com.typesafe.config.ConfigFactory
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory

/**
 * Created by thiago on 3/26/15.
 *
 * Trait that communicates with Neo4J
 *
 */
trait Neo4jConfig {

  private val config = ConfigFactory.load()
  private val graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(config.getString("neo4j.path"))
  private def registerShutdownHook = Runtime.getRuntime.addShutdownHook( new Thread() { override def run() = shutdown })
  private def shutdown = graphDb.shutdown()

  /**
   *
   * The doTransaction method is a high order scala function to wrap the Neo4J transaction system,
   * in other words, high order functions enable functions to receive functions as parameters.
   *
   * This design enable you to pass a function eg createNode inside a transaction
   *
   * @param f The Neo4J function to be passed through the transaction
   */
  def doTransaction(f: GraphDatabaseService => Unit) = {
    val tx = graphDb.beginTx()

    try {
      f(graphDb)
      tx.success()
    } finally {
      registerShutdownHook
      tx.close()
    }
  }
}