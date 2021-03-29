package homeschool

import java.util.concurrent.TimeUnit

import com.datastax.driver.core.{Cluster, SimpleStatement}

import scala.io.Source

class Store(url: String) {
  val cluster = Cluster.builder.addContactPoint(url).build()
  val asyncSession = cluster.connectAsync("event").get(10, TimeUnit.SECONDS)
  val session = cluster.connect("query")

  def load(fileClasspath: String): Unit = {
    val statements = Source.fromInputStream(getClass.getResourceAsStream(fileClasspath)).mkString.split(";").map(s => new SimpleStatement(s)).toIterable
    statements foreach { statement => session.execute(statement) }
  }

  def close(): Unit = {
    asyncSession.close()
    session.close()
    cluster.close()
  }
}