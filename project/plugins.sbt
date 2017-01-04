logLevel := Level.Warn

resolvers += Resolver.sonatypeRepo("releases")

// Code formatting
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "0.5.2-RC1")

// Documenation
addSbtPlugin("com.fortysevendeg" % "sbt-microsites" % "0.3.3")
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.3")
addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.5.4")

// Release
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")
addSbtPlugin("ch.epfl.scala.index" % "sbt-scaladex" % "0.1.3")

// Copyright headers
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.6.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5")
