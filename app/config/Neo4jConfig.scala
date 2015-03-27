package config

import com.typesafe.config.ConfigFactory
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory

/**
 * Created by thiago on 3/26/15.
 */
trait Neo4jConfig {

  private val config = ConfigFactory.load()
  private val graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(config.getString("neo4j.path"))
  private def registerShutdownHook = Runtime.getRuntime.addShutdownHook( new Thread() { override def run() = shutdown })
  private def shutdown = graphDb.shutdown()

  def doTransaction(f: GraphDatabaseService => Unit) = {
    registerShutdownHook
    val tx = graphDb.beginTx()
    try {
      f(graphDb)
      tx.success()
    } finally {
      tx.close()
    }
  }
}