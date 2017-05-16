name := """LiveTemplateSelector"""

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.12.2" % "test"
)

scalacOptions += "-target:jvm-1.8"