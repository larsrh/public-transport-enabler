lazy val standardSettings = Seq(
  organization := "info.hupel.fork.oeffi",
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
  resourceDirectory in Compile := baseDirectory.value / "resources"
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
