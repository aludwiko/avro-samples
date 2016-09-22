resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "bintray-sbt-plugin-releases" at "http://dl.bintray.com/content/sbt/sbt-plugin-releases"

// enable updating file headers eg. for copyright
// https://github.com/sbt/sbt-header
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.5.1")

// Code formatter
addSbtPlugin("com.geirsson" %% "sbt-scalafmt" % "0.2.11")



