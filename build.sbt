name := "homeschool.cassandra"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.4.0-RC3"
libraryDependencies ++= {
  val cassandraVersion = "3.11.5" // Don't upgrade due to big changes with 4.0.0!!!
  Seq(
    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraVersion,
    "com.datastax.cassandra" % "cassandra-driver-mapping" % cassandraVersion,
    "ch.qos.logback" % "logback-classic" % "1.4.14",
    "org.scalatest" %% "scalatest" % "3.2.17" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
