name := "homeschool.cassandra"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.8"
libraryDependencies ++= {
  val cassandraVersion = "3.11.2"
  Seq(
    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraVersion,
    "com.datastax.cassandra" % "cassandra-driver-mapping" % cassandraVersion,
    "ch.qos.logback" % "logback-classic" % "1.4.1",
    "org.scalatest" %% "scalatest" % "3.2.13" % Test
  )
}
