name := "homeschool.cassandra"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.8.0-RC5"
libraryDependencies ++= {
  val cassandraVersion = "3.11.5" // Don't upgrade due to big changes with 4.0.0!!!
  Seq(
    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraVersion,
    "com.datastax.cassandra" % "cassandra-driver-mapping" % cassandraVersion,
    "ch.qos.logback" % "logback-classic" % "1.5.22",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
