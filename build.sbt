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

lazy val root =
  project.copy(id = "scala-mandrill").in(file(".")).settings(noPublishSettings).aggregate(core, testkit, playjson)

lazy val core = project
  .in(file("core"))
  .settings(publishSettings)
  .settings(
    name := "core",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.9.6",
      "org.joda" % "joda-convert" % "1.8.1",
      "com.chuusai" %% "shapeless" % "2.3.2", // Generic programming for scala
      "org.julienrf" %% "enum" % "3.0" // Enum utilities for ADTs
    )
  )

lazy val testkit = project
  .in(file("test-kit"))
  .settings(publishSettings)
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
  .settings(publishSettings)
  .settings(
    name := "play-json",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.5.10"
    )
  )
  .dependsOn(core, testkit % "test->compile")

lazy val docsMappingsAPIDir = settingKey[String]("Name of subdirectory in site target directory for api docs")

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
    micrositeHomepage := "https://www.gutefrage.net",
    micrositeGithubOwner := "gutefrage",
    micrositeGithubRepo := "scala-mandrill",
    micrositeBaseUrl := "/scala-mandrill",
    micrositeDocumentationUrl := "docs",
    micrositeTwitter := "gutefrage_net",
    micrositeHighlightTheme := "atom-one-light",
    micrositeExtraMdFiles := Map(file("README.md") -> "readme.md"),
    micrositePalette := Map(
                            // primary and secondary color are swapped
                            "brand-primary" -> "#4DB8AF",
                            "brand-secondary" -> "#49A0CC",
                            "brand-tertiary" -> "#222749",
                            "gray-dark" -> "#646767",
                            "gray" -> "#A8ACAD",
                            "gray-light" -> "#CACDCD",
                            "gray-lighter" -> "#EDEEEE",
                            "white-color" -> "#FFFFFF"),
    git.remoteRepo := "git@github.com:gutefrage/scala-mandrill.git",
    autoAPIMappings := true,
    docsMappingsAPIDir := "api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), docsMappingsAPIDir),
    ghpagesNoJekyll := false,
    fork in tut := true,
    fork in (ScalaUnidoc, unidoc) := true,
    includeFilter in makeSite := "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.js" | "*.swf" | "*.yml" | "*.md",
    // dependencies for documentation examples
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-ws" % "2.5.10"
    )
  )
  .dependsOn(core, playjson, testkit)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val publishSettings = Seq(
    homepage := Some(url("https://github.com/gutefrage/scala-mandrill")),
    licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    scmInfo := Some(
      ScmInfo(url("https://www.apache.org/licenses/LICENSE-2.0.html"),
              "scm:git:git@github.com:gutefrage/scala-mandrill.git")),
    autoAPIMappings := true,
    apiURL := Some(url("https://gutefrage.github.io/scala-mandrill/api")),
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
  releaseTagName := s"v${if (releaseUseGlobalVersion.value) (version in ThisBuild).value else version.value}",
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
                                     runTest,
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
