name := "homeschool.cassandra"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.13"
libraryDependencies ++= {
  val cassandraVersion = "3.11.0"
  Seq(
    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraVersion,
    "com.datastax.cassandra" % "cassandra-driver-mapping" % cassandraVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.5",
    "org.scalatest" %% "scalatest" % "3.2.10" % Test
  )
}
