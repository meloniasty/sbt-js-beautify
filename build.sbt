import scalariform.formatter.preferences._
//org.scalastyle.sbt.ScalastylePlugin.Settings

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)

sbtPlugin := true

organization := "it.impossible.sbt"

name := "sbt-js-beautify"

//crossScalaVersions := Seq("2.10.4", "2.11.3")

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.10.4"

resolvers ++= Seq(
    "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/",
    Resolver.url("sbt snapshot plugins", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots"))(Resolver.ivyStylePatterns),
    Resolver.sonatypeRepo("snapshots"),
    "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
    Resolver.mavenLocal
    )

libraryDependencies ++= Seq()

addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % "1.0.2")

publishMavenStyle := false

publishTo := {
  if (isSnapshot.value) Some(Classpaths.sbtPluginSnapshots)
  else Some(Classpaths.sbtPluginReleases)
}

scriptedSettings

scriptedLaunchOpts <+= version apply { v => s"-Dproject.version=$v" }

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)
