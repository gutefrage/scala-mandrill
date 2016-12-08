import com.typesafe.sbt.SbtGhPages.GhPagesKeys._
import sbtunidoc.Plugin.UnidocKeys._
import ReleaseTransformations._

organization in ThisBuild := "net.gutefrage.mandrill"
scalaVersion in ThisBuild := "2.11.8"

licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html"))

scalacOptions in ThisBuild ++= Seq("-target:jvm-1.8",
                                   "-deprecation",
                                   "-encoding",
                                   "UTF-8",
                                   "-feature",
                                   "-language:existentials",
                                   "-language:higherKinds",
                                   "-language:implicitConversions",
                                   "-language:experimental.macros",
                                   "-unchecked",
                                   "-Xfatal-warnings",
                                   "-Xlint",
                                   "-Ywarn-dead-code",
                                   "-Xfuture")

lazy val root = project.copy(id = "scala-mandrill").in(file(".")).aggregate(core, playjson)

lazy val core = project
  .in(file("core"))
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.6",
      "org.joda" % "joda-convert" % "1.8.1",
      "com.chuusai" %% "shapeless" % "2.3.2", // Generic programming for scala
      "org.julienrf" %% "enum" % "3.0" // Enum utilities for ADTs
    )
  )

lazy val test = project
  .in(file("test-kit"))
  .settings(
    name := "test-kit",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.0",
      "org.scalacheck" %% "scalacheck" % "1.13.4"
    )
  )
  .dependsOn(core)

lazy val playjson = project
  .in(file("play-json"))
  .settings(
    name := "play-json",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.5.10"
    )
  )
  .dependsOn(core, test % "test->compile")

lazy val docs = project
  .in(file("docs"))
  .enablePlugins(MicrositesPlugin)
  .settings(noPublishSettings)
  .settings(unidocSettings)
  .settings(ghpages.settings)
  .settings(
    micrositeName := "Scala Mandrill",
    micrositeDescription := "Minimal Scala API for Mandrill",
    micrositeAuthor := "Gutefrage.net GmbH",
    micrositeHomepage := "https://gutefrage.github.io/scala-mandrill",
    micrositeGithubOwner := "gutefrage",
    micrositeGithubRepo := "scala-mandrill",
    micrositeBaseUrl := "/scala-mandrill",
    micrositeDocumentationUrl := "api",
    git.remoteRepo := "git@github.com:gutefrage/scala-mandrill.git",
    autoAPIMappings := true,
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), micrositeDocumentationUrl),
    ghpagesNoJekyll := false,
    fork in tut := true,
    fork in (ScalaUnidoc, unidoc) := true
  )

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val publishSettings = Seq(
    homepage := Some(url("hsi ttps://github.com/typelevel/cats")),
    licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
    scmInfo := Some(ScmInfo(url("https://github.com/typelevel/cats"), "scm:git:git@github.com:typelevel/cats.git")),
    autoAPIMappings := true,
    apiURL := Some(url("http://typelevel.org/cats/api/")),
    pomExtra := (
      <developers>
      <developer>
        <id>muuki88</id>
        <name>Nepomuk Seiler</name>
        <url>https://github.com/muuki88/</url>
      </developer>
    </developers>
    )
  ) ++ credentialSettings ++ sharedPublishSettings ++ sharedReleaseProcess

lazy val credentialSettings = Seq(
  // For Travis CI - see http://www.cakesolutions.net/teamblogs/publishing-artefacts-to-oss-sonatype-nexus-using-sbt-and-travis-ci
  credentials ++= (for {
    username <- Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
  } yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq
)

lazy val sharedPublishSettings = Seq(
  releaseCrossBuild := true,
//  releaseTagName := tagName.value,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := Function.const(false),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("Snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("Releases" at nexus + "service/local/staging/deploy/maven2")
  }
)

lazy val sharedReleaseProcess = Seq(
  releaseProcess := Seq[ReleaseStep](checkSnapshotDependencies,
                                     inquireVersions,
                                     runClean,
                                     releaseStepCommand("validate"),
                                     setReleaseVersion,
                                     commitReleaseVersion,
                                     tagRelease,
                                     publishArtifacts,
                                     setNextVersion,
                                     commitNextVersion,
                                     ReleaseStep(action = Command.process("sonatypeReleaseAll", _),
                                                 enableCrossBuild = true),
                                     pushChanges)
)
