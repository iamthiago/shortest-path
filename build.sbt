name := "wallmart"

version := "1.0"

lazy val `wallmart` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

parallelExecution in ThisBuild := false

libraryDependencies ++= Seq( jdbc , anorm , cache , ws )

libraryDependencies ++= Seq(
  "org.neo4j" % "neo4j" % "2.2.0"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;views*;Routes*;controllers*Reverse*;controllers*javascript*;controller*ref*;"