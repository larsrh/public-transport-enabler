lazy val standardSettings = Seq(
  organization := "info.hupel.fork.oeffi",
  sonatypeProfileName := "info.hupel",
  homepage := Some(url("https://github.com/larsrh/public-transport-enabler/")),
  licenses := Seq(
    "GPL" -> url("http://opensource.org/licenses/GPL-3")
  ),
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  autoScalaLibrary := false,
  crossPaths := false,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  credentials += Credentials(
    Option(System.getProperty("build.publish.credentials")) map (new File(_)) getOrElse (Path.userHome / ".ivy2" / ".credentials")
  ),
  javaSource in Compile := baseDirectory.value / "src",
  javaSource in Test := baseDirectory.value / "test",
  resourceDirectory in Compile := baseDirectory.value / "resources",
  pomExtra := (
    <developers>
      <developer>
        <id>larsrh</id>
        <name>Lars Hupel</name>
        <url>http://lars.hupel.info</url>
      </developer>
    </developers>
    <scm>
      <connection>scm:git:github.com/larsrh/public-transport-enabler.git</connection>
      <developerConnection>scm:git:git@github.com:larsrh/public-transport-enabler.git</developerConnection>
      <url>https://github.com/larsrh/public-transport-enabler</url>
    </scm>
  )
)

lazy val noPublishSettings = Seq(
  publish := (()),
  publishLocal := (()),
  publishArtifact := false
)


// Modules

lazy val root = project.in(file("."))
  .settings(standardSettings)
  .settings(noPublishSettings)
  .aggregate(enabler)

lazy val enabler = project.in(file("enabler"))
  .settings(standardSettings)
  .settings(moduleName := "public-transport-enabler")


// Release stuff

import ReleaseTransformations._

releaseVcsSign := true
releaseCrossBuild := true

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeRelease")
)
