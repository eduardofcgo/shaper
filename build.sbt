name := "shaper"

scalaVersion := "2.13.1"

scalacOptions += "-language:postfixOps"

libraryDependencies ++= Seq (
  "org.scala-lang.modules" % "scala-xml_2.13" % "2.0.1",
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test"
)
