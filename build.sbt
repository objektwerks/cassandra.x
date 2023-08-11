name := "homeschool.cassandra"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.11"
libraryDependencies ++= {
  val cassandraVersion = "3.11.3"
  Seq(
    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraVersion,
    "com.datastax.cassandra" % "cassandra-driver-mapping" % cassandraVersion,
    "ch.qos.logback" % "logback-classic" % "1.4.11",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
