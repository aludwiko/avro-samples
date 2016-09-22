name := """avro-samples"""

version := "1.0"

scalaVersion := "2.11.8"

name := "akka-serialization-test"

organization := "com.giampaolotrapasso"

version := "0.0.1"

scalaVersion := "2.11.8"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val akkaVersion = "2.4.8"
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.7" % Test,
    "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.1",
    "org.apache.avro" % "avro" % "1.8.1",
    "org.json4s" %% "json4s-native" % "3.4.0",
    "org.scalatest" %% "scalatest" % "2.2.6" % Test,
    "org.scalacheck" %% "scalacheck" % "1.12.5" % Test
  )
}

fork in Test := true

scalacOptions ++= Seq("-feature", "-language:higherKinds", "-language:implicitConversions", "-deprecation", "-Ybackend:GenBCode", "-Ydelambdafy:method", "-target:jvm-1.8")

javaOptions in Test ++= Seq("-Xms30m", "-Xmx30m")

parallelExecution in Test := false

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))


licenses +=("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))

